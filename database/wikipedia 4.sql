PGDMP                 	        |         	   Wikipedia    14.5    14.5 M    T           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            U           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            V           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            W           1262    128379 	   Wikipedia    DATABASE     g   CREATE DATABASE "Wikipedia" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Italian_Italy.1252';
    DROP DATABASE "Wikipedia";
                postgres    false            _           1247    128446    stato_proposta    TYPE     r   CREATE TYPE public.stato_proposta AS ENUM (
    'non inviato',
    'inviato',
    'accettato',
    'rifiutato'
);
 !   DROP TYPE public.stato_proposta;
       public          postgres    false            �            1255    128540    after_update_proposta()    FUNCTION     �	  CREATE FUNCTION public.after_update_proposta() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE 
    var INTEGER;
    num_versione_frase INTEGER;
    id_frase_selezionata frase.id_frase%TYPE;
    testo_frase VARCHAR;
	id_proposta_sel proposta.id_proposta%TYPE;
    frasi_modifica CURSOR FOR
        SELECT id_frase, frase_proposta
        FROM modifica 
        WHERE id_proposta = OLD.id_proposta;
    proposte CURSOR FOR
        SELECT id_proposta
        FROM proposta 
        WHERE titolo=NEW.titolo AND stato='inviato' AND matricola<>NEW.matricola
        ORDER BY(data,ora);
BEGIN 
    IF NEW.stato='accettato' AND OLD.stato='non inviato' THEN
        RAISE EXCEPTION 'La proposta ancora non è stata inviata, quindi non può essere gestita';
        RETURN OLD;
    ELSIF NEW.stato='accettato' AND OLD.stato='inviato'  THEN
        OPEN frasi_modifica;
        FETCH frasi_modifica INTO id_frase_selezionata, testo_frase;
        DELETE FROM Proposta WHERE titolo = OLD.Titolo AND OLD.stato = 'non inviato';
        SELECT MAX(num_versione) INTO num_versione_frase
   	FROM Versione NATURAL INNER JOIN Frase
   	WHERE titolo IN(SELECT titolo FROM frase WHERE id_frase=id_frase_selezionata);
	INSERT INTO versione(id_frase, testo, num_versione) VALUES(id_frase_selezionata, testo_frase, num_versione_frase+1);
	LOOP
            FETCH frasi_modifica INTO id_frase_selezionata, testo_frase;
            EXIT WHEN NOT FOUND;
            INSERT INTO versione(id_frase, testo, num_versione) VALUES(id_frase_selezionata, testo_frase, num_versione_frase+1);
        END LOOP;
 
        CLOSE frasi_modifica;
        RETURN NEW;
    ELSIF NEW.stato='rifiutato' AND OLD.stato='inviato' THEN
        RETURN NEW;
    ELSIF NEW.stato='inviato' AND OLD.stato='non inviato' THEN
    	SELECT COUNT(*) INTO var
    	FROM modifica
    	WHERE id_proposta=new.id_proposta;
    	IF var=0 THEN
    		RAISE EXCEPTION 'Proposta senza nessuna modifica proposta';
    		RETURN OLD;
    	END IF;
    	SELECT COUNT(*) INTO var
    	FROM Pagina
   	 WHERE titolo = NEW.titolo AND matricola = NEW.matricola;
    	IF var> 0 THEN
    		OPEN proposte;
		LOOP
		FETCH proposte INTO id_proposta_sel;
		EXIT WHEN NOT FOUND;
		UPDATE proposta SET stato='rifiutato' WHERE id_proposta=id_proposta_sel;
		END LOOP;
		CLOSE proposte;
        UPDATE proposta SET stato = 'accettato' WHERE id_proposta = NEW.id_proposta;
    	END IF;	
    	RETURN NEW;
    ELSE 
        RETURN OLD;
    END IF;
END;
$$;
 .   DROP FUNCTION public.after_update_proposta();
       public          postgres    false            �            1255    128530 l   aggiungi_autore(character varying, character varying, date, character, character varying, character varying) 	   PROCEDURE     �  CREATE PROCEDURE public.aggiungi_autore(IN nome_utente character varying, IN cognome_utente character varying, IN data_nascita_utente date, IN matricola_utente character, IN username_autore character varying, IN password_autore character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
call aggiungi_utente(nome_utente, cognome_utente, data_nascita_utente, matricola_utente);
INSERT INTO autore(username, matricola, password)
VALUES(username_autore, matricola_utente, password_autore);
END
$$;
 �   DROP PROCEDURE public.aggiungi_autore(IN nome_utente character varying, IN cognome_utente character varying, IN data_nascita_utente date, IN matricola_utente character, IN username_autore character varying, IN password_autore character varying);
       public          postgres    false            �            1255    128529 F   aggiungi_utente(character varying, character varying, date, character) 	   PROCEDURE     ]  CREATE PROCEDURE public.aggiungi_utente(IN nome_utente character varying, IN cognome_utente character varying, IN data_nascita_utente date, IN matricola_utente character)
    LANGUAGE plpgsql
    AS $$
BEGIN
INSERT INTO utente(nome,cognome,data_nascita, matricola)
VALUES(nome_utente, cognome_utente, data_nascita_utente, matricola_utente);
END
$$;
 �   DROP PROCEDURE public.aggiungi_utente(IN nome_utente character varying, IN cognome_utente character varying, IN data_nascita_utente date, IN matricola_utente character);
       public          postgres    false            �            1255    128537    before_delete_modifica()    FUNCTION     �  CREATE FUNCTION public.before_delete_modifica() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
    stato_proposta_attuale proposta.stato%TYPE;
BEGIN
    SELECT stato INTO stato_proposta_attuale
    FROM proposta
    WHERE id_proposta = OLD.id_proposta;

    IF stato_proposta_attuale <> 'non inviato' THEN
        RAISE EXCEPTION 'Proposta già inviata all autore, non può essere ulteriormente modificata';
        RETURN OLD;
    ELSE 
        RETURN NEW;
    END IF;
END;
$$;
 /   DROP FUNCTION public.before_delete_modifica();
       public          postgres    false            �            1255    128542    before_gestione_proposta()    FUNCTION     �  CREATE FUNCTION public.before_gestione_proposta() RETURNS trigger
    LANGUAGE plpgsql
    AS $$ --va (DA PROVARE ANCORA)
BEGIN
    IF (old.stato='inviato' AND (NEW.stato='accettato' OR NEW.stato='rifiutato')) THEN

	IF EXISTS (
        SELECT 1
        FROM proposta
        WHERE titolo = NEW.Titolo 
            AND (data < NEW.Data OR (Data = NEW.Data AND Ora < NEW.Ora)) 
            AND stato = 'inviato' 
            AND id_proposta <> OLD.id_proposta
    ) THEN
		RAISE EXCEPTION 'Ci sono altre proposte ancora da dover gestire, meno recenti';
        RETURN OLD;
    ELSE
        RETURN NEW;
    END IF;
END IF;
    RETURN NEW;
END;
$$;
 1   DROP FUNCTION public.before_gestione_proposta();
       public          postgres    false            �            1255    128532    before_insert_frase()    FUNCTION     �  CREATE FUNCTION public.before_insert_frase() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
    ordine_max frase.ordine%TYPE;
    num_righe INTEGER;
BEGIN
    SELECT COUNT(*) INTO num_righe
    FROM Frase
    WHERE titolo = NEW.titolo;

    IF num_righe = 0 THEN
        NEW.Ordine := 1;
    ELSE
        SELECT MAX(Ordine) INTO ordine_max
        FROM Frase
        WHERE titolo = NEW.titolo;
 
        NEW.Ordine := ordine_max + 1;
    END IF;

    RETURN NEW;
END;
$$;
 ,   DROP FUNCTION public.before_insert_frase();
       public          postgres    false            �            1255    128535    before_modifica()    FUNCTION     '  CREATE FUNCTION public.before_modifica() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
    stato_proposta_attuale proposta.stato%TYPE;
    var INTEGER;
BEGIN
    SELECT stato INTO stato_proposta_attuale
    FROM proposta
    WHERE id_proposta = NEW.id_proposta;
    
    SELECT COUNT(*) INTO var
    FROM modifica 
    WHERE id_frase = NEW.id_frase AND id_proposta = NEW.id_proposta;

    IF stato_proposta_attuale <> 'non inviato' THEN
        RAISE EXCEPTION 'Proposta già inviata all autore, non può essere ulteriormente modificata';
        RETURN OLD;
    ELSIF var > 0 THEN
        UPDATE modifica
        SET frase_proposta = NEW.frase_proposta 
        WHERE id_frase = NEW.id_frase AND id_proposta = NEW.id_proposta;
        RETURN OLD;
    ELSE
        RETURN NEW;
    END IF;
END;
$$;
 (   DROP FUNCTION public.before_modifica();
       public          postgres    false            �            1255    128528 <   crea_pagina(character varying, character, character varying) 	   PROCEDURE     *  CREATE PROCEDURE public.crea_pagina(IN titolo_pagina character varying, IN matricola_autore character, IN username_autore character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
    INSERT INTO pagina(titolo, username, matricola) VALUES (titolo_pagina, username_autore, matricola_autore);
END;
$$;
 �   DROP PROCEDURE public.crea_pagina(IN titolo_pagina character varying, IN matricola_autore character, IN username_autore character varying);
       public          postgres    false            �            1255    128539 E   inserisci_collegamento(character varying, character varying, integer) 	   PROCEDURE     �  CREATE PROCEDURE public.inserisci_collegamento(IN titolo_pagina_sorgente character varying, IN titolo_pagina_destinazione character varying, IN ordine_frase integer)
    LANGUAGE plpgsql
    AS $$

DECLARE

    id_frase_collegamento Frase.id_frase%TYPE;

BEGIN 
 
    SELECT id_Frase INTO id_frase_collegamento FROM frase WHERE titolo = titolo_pagina_sorgente AND ordine = ordine_frase;
 
    INSERT INTO collegamento(id_Frase, Titolo) VALUES(id_frase_collegamento, titolo_pagina_destinazione);

END;

$$;
 �   DROP PROCEDURE public.inserisci_collegamento(IN titolo_pagina_sorgente character varying, IN titolo_pagina_destinazione character varying, IN ordine_frase integer);
       public          postgres    false            �            1255    128531 5   inserisci_frase(character varying, character varying) 	   PROCEDURE     }  CREATE PROCEDURE public.inserisci_frase(IN titolo_pagina character varying, IN testo_frase character varying)
    LANGUAGE plpgsql
    AS $$ --ok
DECLARE
    id_frase_nuova INTEGER;
    var INTEGER;
    num_versione_frase INTEGER :=1;
BEGIN 
    
    INSERT INTO frase(titolo) VALUES(titolo_pagina) RETURNING id_frase INTO id_frase_nuova;
    SELECT MAX(num_versione) INTO var
    FROM Versione NATURAL INNER JOIN Frase
    WHERE titolo = titolo_Pagina;
    IF var>1 THEN
    	num_versione_frase=var+1;
    END IF;
    INSERT INTO versione(id_frase, testo, num_versione) VALUES(id_frase_nuova, testo_frase, num_versione_frase);
END;
$$;
 m   DROP PROCEDURE public.inserisci_frase(IN titolo_pagina character varying, IN testo_frase character varying);
       public          postgres    false            �            1255    128534 5   proponi_modifica(integer, integer, character varying) 	   PROCEDURE     �  CREATE PROCEDURE public.proponi_modifica(IN posizione integer, IN id_proposta_modifica integer, IN modifica_proposta character varying)
    LANGUAGE plpgsql
    AS $$
DECLARE 
    titolo_pagina pagina.titolo%TYPE;
    id_frase_selezionata frase.id_frase%TYPE;
BEGIN
    SELECT titolo INTO titolo_pagina 
    FROM proposta 
    WHERE id_proposta = id_proposta_modifica;

    SELECT f.id_frase INTO id_frase_selezionata 
    FROM frase f JOIN pagina p ON f.titolo = p.titolo
    WHERE p.titolo = titolo_pagina AND f.ordine=posizione;

    INSERT INTO modifica(frase_proposta, id_proposta, id_frase)
    VALUES(modifica_proposta, id_proposta_modifica, id_frase_selezionata);
END;
$$;
 �   DROP PROCEDURE public.proponi_modifica(IN posizione integer, IN id_proposta_modifica integer, IN modifica_proposta character varying);
       public          postgres    false            �            1255    128544 $   visualizza_pagina(character varying)    FUNCTION     �  CREATE FUNCTION public.visualizza_pagina(titolo_pagina character varying) RETURNS TABLE(testo_frase character varying, titolo_collegamento_destinazione character varying)
    LANGUAGE plpgsql
    AS $$

BEGIN
    

    RETURN QUERY 
    SELECT v.testo AS testo_frase,
           CASE
               WHEN c.Titolo IS NOT NULL THEN c.Titolo
               ELSE 'NO LINK'
           END AS Titolo_collegamento_destinazione
    FROM Versione v
    JOIN Frase f ON v.id_frase = f.id_Frase
    LEFT JOIN Collegamento c ON f.id_frase = c.id_frase
    WHERE f.titolo = titolo_pagina
        AND v.num_versione = (
            SELECT MAX(v2.num_versione)
            FROM Versione v2
            WHERE v2.id_frase = f.id_frase
        )
    ORDER BY f.ordine;

END;
$$;
 I   DROP FUNCTION public.visualizza_pagina(titolo_pagina character varying);
       public          postgres    false            �            1255    128547 R   visualizza_pagina_autore(character varying, character, character varying, integer)    FUNCTION     I  CREATE FUNCTION public.visualizza_pagina_autore(username_autore character varying, matricola_autore character, titolo_pagina character varying, numero_versione integer) RETURNS TABLE(titolo_pag character varying, testo_frase character varying, n_versione integer)
    LANGUAGE plpgsql
    AS $$
DECLARE
    tipo_pagina TEXT;
BEGIN
    tipo_pagina := (
        SELECT 'Creata'
        FROM Pagina
        WHERE username = username_autore AND matricola = matricola_autore AND Titolo = titolo_pagina
        UNION
        SELECT 'Proposta'
        FROM Modifica m
        JOIN Proposta pr ON m.id_proposta = pr.id_proposta
        WHERE pr.Matricola = matricola_autore AND pr.Titolo = titolo_pagina
        LIMIT 1
    );

    IF tipo_pagina IS NULL THEN
        RAISE EXCEPTION 'Non hai accesso a questa pagina';
    ELSE
        RETURN QUERY 
        SELECT 
            p.Titolo AS titolo_pag,
            v.testo AS testo_frase,
            v.num_versione
        FROM versione v
        JOIN Frase f ON v.id_frase = f.id_frase
        JOIN Pagina p ON f.titolo = p.titolo
        WHERE p.Titolo = titolo_pagina AND v.num_versione <= numero_versione
        AND v.num_versione = (
            SELECT MAX(num_versione)
            FROM Versione
            WHERE id_frase = f.id_frase AND num_versione <= numero_versione
        );
    END IF;
END;
$$;
 �   DROP FUNCTION public.visualizza_pagina_autore(username_autore character varying, matricola_autore character, titolo_pagina character varying, numero_versione integer);
       public          postgres    false            �            1255    128546 ?   visualizza_storico_autore(character varying, character varying)    FUNCTION     X  CREATE FUNCTION public.visualizza_storico_autore(username_autore character varying, matricola_autore character varying) RETURNS TABLE(tipo_pagina text, titolo_pagina character varying, num_versione integer)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY 
    SELECT 
        'Creata' AS tipo_pagina,
        p.Titolo AS titolo_pagina,
        MAX(v.num_versione) AS num_versione
    FROM Pagina p
    JOIN Frase f ON p.titolo = f.titolo
    JOIN Versione v ON f.id_Frase = v.id_Frase
    WHERE p.username = username_autore AND p.matricola = matricola_autore
    GROUP BY p.Titolo
    
    UNION
    
    SELECT 
        'Proposta' AS tipo_pagina,
        p.Titolo AS titolo_pagina,
        MAX(v.num_versione) AS num_versione
    FROM Pagina p
    JOIN Frase f ON p.titolo = f.titolo
    JOIN Versione v ON f.id_Frase = v.id_Frase
    JOIN Modifica m ON m.id_frase = f.id_frase
    JOIN Proposta pr ON m.id_proposta = pr.id_proposta
    WHERE pr.Matricola = matricola_autore AND p.matricola != matricola_autore AND p.username != username_autore
    GROUP BY p.Titolo
    ORDER BY titolo_pagina;

END;
$$;
 w   DROP FUNCTION public.visualizza_storico_autore(username_autore character varying, matricola_autore character varying);
       public          postgres    false            �            1255    128545 W   visualizza_versione_e_proposte(character varying, character varying, character varying)    FUNCTION     c  CREATE FUNCTION public.visualizza_versione_e_proposte(username_autore character varying, matricola_autore character varying, titolo_pagina character varying) RETURNS TABLE(ordine integer, versione_corrente character varying, versione_proposta character varying)
    LANGUAGE plpgsql
    AS $$
DECLARE

    id_proposta_recente INTEGER;
    autore_count INT;
BEGIN
    -- Controlla se l'autore è associato alla pagina
    SELECT COUNT(*)
    INTO autore_count
    FROM Pagina
    WHERE Titolo = titolo_pagina
        AND Matricola = matricola_autore
        AND username = username_autore;

    -- Se l'autore non è associato alla pagina, lancia un'eccezione
    IF autore_count = 0 THEN
        RAISE EXCEPTION 'autore specificato non associato a questa pagina.';
    ELSE
    	SELECT id_proposta INTO id_proposta_recente 
    	FROM proposta
    	WHERE titolo=titolo_pagina AND stato='inviato';

        RETURN QUERY
    

SELECT f.ordine, v.testo AS versione_corrente, m.frase_proposta AS versione_proposta
FROM frase f
JOIN versione v ON f.id_frase = v.id_frase
JOIN modifica m ON f.id_frase = m.id_frase
WHERE m.id_proposta = id_proposta_recente 
    AND f.titolo = titolo_pagina
    AND v.num_versione IN (
        SELECT MAX(v1.num_versione) 
        FROM versione v1 
        WHERE v1.id_frase = f.id_frase
    )

UNION

SELECT f.ordine, v.testo AS versione_corrente, v.testo AS versione_proposta
FROM frase f
JOIN versione v ON f.id_frase = v.id_frase
WHERE f.titolo = titolo_pagina
    AND v.num_versione IN (
        SELECT MAX(v1.num_versione) 
        FROM versione v1 
        WHERE v1.id_frase = f.id_frase
    )
    AND (f.ordine, v.testo) NOT IN (
        SELECT f1.ordine, v1.testo
        FROM frase f1
        JOIN versione v1 ON f1.id_frase = v1.id_frase
        JOIN modifica m1 ON f1.id_frase = m1.id_frase
        WHERE m1.id_proposta = id_proposta_recente 
            AND f1.titolo = titolo_pagina 
            AND v1.num_versione IN (
                SELECT MAX(v2.num_versione) 
                FROM versione v2 
                WHERE v2.id_frase = f1.id_frase
            )
    )
ORDER BY ordine;

    END IF;
END;
$$;
 �   DROP FUNCTION public.visualizza_versione_e_proposte(username_autore character varying, matricola_autore character varying, titolo_pagina character varying);
       public          postgres    false            �            1259    128387    autore    TABLE     �   CREATE TABLE public.autore (
    username character varying NOT NULL,
    matricola character varying NOT NULL,
    password character varying NOT NULL
);
    DROP TABLE public.autore;
       public         heap    postgres    false            �            1259    128497    collegamento    TABLE     �   CREATE TABLE public.collegamento (
    id_collegamento integer NOT NULL,
    id_frase integer NOT NULL,
    titolo character varying NOT NULL
);
     DROP TABLE public.collegamento;
       public         heap    postgres    false            �            1259    128496     collegamento_id_collegamento_seq    SEQUENCE     �   CREATE SEQUENCE public.collegamento_id_collegamento_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 7   DROP SEQUENCE public.collegamento_id_collegamento_seq;
       public          postgres    false    220            X           0    0     collegamento_id_collegamento_seq    SEQUENCE OWNED BY     e   ALTER SEQUENCE public.collegamento_id_collegamento_seq OWNED BY public.collegamento.id_collegamento;
          public          postgres    false    219            �            1259    128420    frase    TABLE     x   CREATE TABLE public.frase (
    id_frase integer NOT NULL,
    titolo character varying NOT NULL,
    ordine integer
);
    DROP TABLE public.frase;
       public         heap    postgres    false            �            1259    128419    frase_id_frase_seq    SEQUENCE     �   CREATE SEQUENCE public.frase_id_frase_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.frase_id_frase_seq;
       public          postgres    false    213            Y           0    0    frase_id_frase_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.frase_id_frase_seq OWNED BY public.frase.id_frase;
          public          postgres    false    212            �            1259    128478    modifica    TABLE     �   CREATE TABLE public.modifica (
    id_modifica integer NOT NULL,
    frase_proposta character varying,
    id_frase integer NOT NULL,
    id_proposta integer NOT NULL
);
    DROP TABLE public.modifica;
       public         heap    postgres    false            �            1259    128477    modifica_id_modifica_seq    SEQUENCE     �   CREATE SEQUENCE public.modifica_id_modifica_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.modifica_id_modifica_seq;
       public          postgres    false    218            Z           0    0    modifica_id_modifica_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public.modifica_id_modifica_seq OWNED BY public.modifica.id_modifica;
          public          postgres    false    217            �            1259    128403    pagina    TABLE     {  CREATE TABLE public.pagina (
    titolo character varying NOT NULL,
    data_creazione date DEFAULT CURRENT_DATE,
    ora_creazione time without time zone DEFAULT CURRENT_TIME,
    data_aggiornamento date DEFAULT CURRENT_DATE,
    ora_aggiornamento time without time zone DEFAULT CURRENT_TIME,
    matricola character varying NOT NULL,
    username character varying NOT NULL
);
    DROP TABLE public.pagina;
       public         heap    postgres    false            �            1259    128456    proposta    TABLE     ;  CREATE TABLE public.proposta (
    id_proposta integer NOT NULL,
    data date DEFAULT CURRENT_DATE,
    ora time without time zone DEFAULT CURRENT_TIME,
    stato public.stato_proposta DEFAULT 'non inviato'::public.stato_proposta,
    titolo character varying NOT NULL,
    matricola character varying NOT NULL
);
    DROP TABLE public.proposta;
       public         heap    postgres    false    863    863            �            1259    128455    proposta_id_proposta_seq    SEQUENCE     �   CREATE SEQUENCE public.proposta_id_proposta_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.proposta_id_proposta_seq;
       public          postgres    false    216            [           0    0    proposta_id_proposta_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public.proposta_id_proposta_seq OWNED BY public.proposta.id_proposta;
          public          postgres    false    215            �            1259    128380    utente    TABLE     �   CREATE TABLE public.utente (
    matricola character(9) NOT NULL,
    nome character varying NOT NULL,
    cognome character varying NOT NULL,
    data_nascita date
);
    DROP TABLE public.utente;
       public         heap    postgres    false            �            1259    128433    versione    TABLE     �   CREATE TABLE public.versione (
    testo character varying,
    num_versione integer NOT NULL,
    id_frase integer NOT NULL
);
    DROP TABLE public.versione;
       public         heap    postgres    false            �           2604    128500    collegamento id_collegamento    DEFAULT     �   ALTER TABLE ONLY public.collegamento ALTER COLUMN id_collegamento SET DEFAULT nextval('public.collegamento_id_collegamento_seq'::regclass);
 K   ALTER TABLE public.collegamento ALTER COLUMN id_collegamento DROP DEFAULT;
       public          postgres    false    219    220    220            �           2604    128423    frase id_frase    DEFAULT     p   ALTER TABLE ONLY public.frase ALTER COLUMN id_frase SET DEFAULT nextval('public.frase_id_frase_seq'::regclass);
 =   ALTER TABLE public.frase ALTER COLUMN id_frase DROP DEFAULT;
       public          postgres    false    213    212    213            �           2604    128481    modifica id_modifica    DEFAULT     |   ALTER TABLE ONLY public.modifica ALTER COLUMN id_modifica SET DEFAULT nextval('public.modifica_id_modifica_seq'::regclass);
 C   ALTER TABLE public.modifica ALTER COLUMN id_modifica DROP DEFAULT;
       public          postgres    false    218    217    218            �           2604    128459    proposta id_proposta    DEFAULT     |   ALTER TABLE ONLY public.proposta ALTER COLUMN id_proposta SET DEFAULT nextval('public.proposta_id_proposta_seq'::regclass);
 C   ALTER TABLE public.proposta ALTER COLUMN id_proposta DROP DEFAULT;
       public          postgres    false    216    215    216            G          0    128387    autore 
   TABLE DATA           ?   COPY public.autore (username, matricola, password) FROM stdin;
    public          postgres    false    210   k�       Q          0    128497    collegamento 
   TABLE DATA           I   COPY public.collegamento (id_collegamento, id_frase, titolo) FROM stdin;
    public          postgres    false    220   ސ       J          0    128420    frase 
   TABLE DATA           9   COPY public.frase (id_frase, titolo, ordine) FROM stdin;
    public          postgres    false    213   A�       O          0    128478    modifica 
   TABLE DATA           V   COPY public.modifica (id_modifica, frase_proposta, id_frase, id_proposta) FROM stdin;
    public          postgres    false    218   ��       H          0    128403    pagina 
   TABLE DATA           �   COPY public.pagina (titolo, data_creazione, ora_creazione, data_aggiornamento, ora_aggiornamento, matricola, username) FROM stdin;
    public          postgres    false    211   "�       M          0    128456    proposta 
   TABLE DATA           T   COPY public.proposta (id_proposta, data, ora, stato, titolo, matricola) FROM stdin;
    public          postgres    false    216   �       F          0    128380    utente 
   TABLE DATA           H   COPY public.utente (matricola, nome, cognome, data_nascita) FROM stdin;
    public          postgres    false    209   x�       K          0    128433    versione 
   TABLE DATA           A   COPY public.versione (testo, num_versione, id_frase) FROM stdin;
    public          postgres    false    214   <�       \           0    0     collegamento_id_collegamento_seq    SEQUENCE SET     O   SELECT pg_catalog.setval('public.collegamento_id_collegamento_seq', 10, true);
          public          postgres    false    219            ]           0    0    frase_id_frase_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.frase_id_frase_seq', 14, true);
          public          postgres    false    212            ^           0    0    modifica_id_modifica_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.modifica_id_modifica_seq', 16, true);
          public          postgres    false    217            _           0    0    proposta_id_proposta_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.proposta_id_proposta_seq', 5, true);
          public          postgres    false    215            �           2606    128397    autore autore_matricola_key 
   CONSTRAINT     [   ALTER TABLE ONLY public.autore
    ADD CONSTRAINT autore_matricola_key UNIQUE (matricola);
 E   ALTER TABLE ONLY public.autore DROP CONSTRAINT autore_matricola_key;
       public            postgres    false    210            �           2606    128393    autore autore_pk 
   CONSTRAINT     _   ALTER TABLE ONLY public.autore
    ADD CONSTRAINT autore_pk PRIMARY KEY (username, matricola);
 :   ALTER TABLE ONLY public.autore DROP CONSTRAINT autore_pk;
       public            postgres    false    210    210            �           2606    128395    autore autore_username_key 
   CONSTRAINT     Y   ALTER TABLE ONLY public.autore
    ADD CONSTRAINT autore_username_key UNIQUE (username);
 D   ALTER TABLE ONLY public.autore DROP CONSTRAINT autore_username_key;
       public            postgres    false    210            �           2606    128504    collegamento collegamento_pkey 
   CONSTRAINT     i   ALTER TABLE ONLY public.collegamento
    ADD CONSTRAINT collegamento_pkey PRIMARY KEY (id_collegamento);
 H   ALTER TABLE ONLY public.collegamento DROP CONSTRAINT collegamento_pkey;
       public            postgres    false    220            �           2606    128427    frase frase_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.frase
    ADD CONSTRAINT frase_pkey PRIMARY KEY (id_frase);
 :   ALTER TABLE ONLY public.frase DROP CONSTRAINT frase_pkey;
       public            postgres    false    213            �           2606    128485    modifica modifica_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.modifica
    ADD CONSTRAINT modifica_pkey PRIMARY KEY (id_modifica);
 @   ALTER TABLE ONLY public.modifica DROP CONSTRAINT modifica_pkey;
       public            postgres    false    218            �           2606    128413    pagina pagina_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.pagina
    ADD CONSTRAINT pagina_pkey PRIMARY KEY (titolo);
 <   ALTER TABLE ONLY public.pagina DROP CONSTRAINT pagina_pkey;
       public            postgres    false    211            �           2606    128466    proposta proposta_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.proposta
    ADD CONSTRAINT proposta_pkey PRIMARY KEY (id_proposta);
 @   ALTER TABLE ONLY public.proposta DROP CONSTRAINT proposta_pkey;
       public            postgres    false    216            �           2606    128386    utente utente_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.utente
    ADD CONSTRAINT utente_pkey PRIMARY KEY (matricola);
 <   ALTER TABLE ONLY public.utente DROP CONSTRAINT utente_pkey;
       public            postgres    false    209            �           2606    128439    versione versione_pk 
   CONSTRAINT     f   ALTER TABLE ONLY public.versione
    ADD CONSTRAINT versione_pk PRIMARY KEY (id_frase, num_versione);
 >   ALTER TABLE ONLY public.versione DROP CONSTRAINT versione_pk;
       public            postgres    false    214    214            �           2620    128541    proposta after_update_proposta    TRIGGER     �   CREATE TRIGGER after_update_proposta AFTER UPDATE ON public.proposta FOR EACH ROW EXECUTE FUNCTION public.after_update_proposta();
 7   DROP TRIGGER after_update_proposta ON public.proposta;
       public          postgres    false    216    241            �           2620    128538    modifica before_delete_modifica    TRIGGER     �   CREATE TRIGGER before_delete_modifica BEFORE DELETE OR UPDATE ON public.modifica FOR EACH ROW EXECUTE FUNCTION public.before_delete_modifica();
 8   DROP TRIGGER before_delete_modifica ON public.modifica;
       public          postgres    false    239    218            �           2620    128543 !   proposta before_gestione_proposta    TRIGGER     �   CREATE TRIGGER before_gestione_proposta BEFORE UPDATE ON public.proposta FOR EACH ROW EXECUTE FUNCTION public.before_gestione_proposta();
 :   DROP TRIGGER before_gestione_proposta ON public.proposta;
       public          postgres    false    216    242            �           2620    128533    frase before_insert_frase    TRIGGER     }   CREATE TRIGGER before_insert_frase BEFORE INSERT ON public.frase FOR EACH ROW EXECUTE FUNCTION public.before_insert_frase();
 2   DROP TRIGGER before_insert_frase ON public.frase;
       public          postgres    false    236    213            �           2620    128536    modifica before_modifica    TRIGGER     x   CREATE TRIGGER before_modifica BEFORE INSERT ON public.modifica FOR EACH ROW EXECUTE FUNCTION public.before_modifica();
 1   DROP TRIGGER before_modifica ON public.modifica;
       public          postgres    false    218    238            �           2606    128398    autore autore_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.autore
    ADD CONSTRAINT autore_fk FOREIGN KEY (matricola) REFERENCES public.utente(matricola) ON UPDATE CASCADE ON DELETE CASCADE;
 :   ALTER TABLE ONLY public.autore DROP CONSTRAINT autore_fk;
       public          postgres    false    3225    210    209            �           2606    128505    collegamento collegamento_fk1    FK CONSTRAINT     �   ALTER TABLE ONLY public.collegamento
    ADD CONSTRAINT collegamento_fk1 FOREIGN KEY (id_frase) REFERENCES public.frase(id_frase) ON UPDATE CASCADE ON DELETE CASCADE;
 G   ALTER TABLE ONLY public.collegamento DROP CONSTRAINT collegamento_fk1;
       public          postgres    false    3235    213    220            �           2606    128510    collegamento collegamento_fk2    FK CONSTRAINT     �   ALTER TABLE ONLY public.collegamento
    ADD CONSTRAINT collegamento_fk2 FOREIGN KEY (titolo) REFERENCES public.pagina(titolo) ON UPDATE CASCADE ON DELETE CASCADE;
 G   ALTER TABLE ONLY public.collegamento DROP CONSTRAINT collegamento_fk2;
       public          postgres    false    211    220    3233            �           2606    128428    frase frase_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.frase
    ADD CONSTRAINT frase_fk FOREIGN KEY (titolo) REFERENCES public.pagina(titolo) ON UPDATE CASCADE ON DELETE CASCADE;
 8   ALTER TABLE ONLY public.frase DROP CONSTRAINT frase_fk;
       public          postgres    false    211    213    3233            �           2606    128486    modifica modifica_fk1    FK CONSTRAINT     {   ALTER TABLE ONLY public.modifica
    ADD CONSTRAINT modifica_fk1 FOREIGN KEY (id_frase) REFERENCES public.frase(id_frase);
 ?   ALTER TABLE ONLY public.modifica DROP CONSTRAINT modifica_fk1;
       public          postgres    false    3235    213    218            �           2606    128491    modifica modifica_fk2    FK CONSTRAINT     �   ALTER TABLE ONLY public.modifica
    ADD CONSTRAINT modifica_fk2 FOREIGN KEY (id_proposta) REFERENCES public.proposta(id_proposta);
 ?   ALTER TABLE ONLY public.modifica DROP CONSTRAINT modifica_fk2;
       public          postgres    false    3239    218    216            �           2606    128414    pagina pagina_fk1    FK CONSTRAINT     �   ALTER TABLE ONLY public.pagina
    ADD CONSTRAINT pagina_fk1 FOREIGN KEY (matricola, username) REFERENCES public.autore(matricola, username) ON UPDATE CASCADE ON DELETE CASCADE;
 ;   ALTER TABLE ONLY public.pagina DROP CONSTRAINT pagina_fk1;
       public          postgres    false    210    211    211    210    3229            �           2606    128467    proposta proposta_fk3    FK CONSTRAINT     �   ALTER TABLE ONLY public.proposta
    ADD CONSTRAINT proposta_fk3 FOREIGN KEY (matricola) REFERENCES public.utente(matricola) ON UPDATE CASCADE ON DELETE SET NULL;
 ?   ALTER TABLE ONLY public.proposta DROP CONSTRAINT proposta_fk3;
       public          postgres    false    216    3225    209            �           2606    128472    proposta proposta_fk4    FK CONSTRAINT     �   ALTER TABLE ONLY public.proposta
    ADD CONSTRAINT proposta_fk4 FOREIGN KEY (titolo) REFERENCES public.pagina(titolo) ON UPDATE CASCADE;
 ?   ALTER TABLE ONLY public.proposta DROP CONSTRAINT proposta_fk4;
       public          postgres    false    216    211    3233            �           2606    128440    versione versione_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.versione
    ADD CONSTRAINT versione_fk FOREIGN KEY (id_frase) REFERENCES public.frase(id_frase) ON UPDATE CASCADE ON DELETE CASCADE;
 >   ALTER TABLE ONLY public.versione DROP CONSTRAINT versione_fk;
       public          postgres    false    214    3235    213            G   c   x�=�I
� D�u�aB���d��CP$׏ �~U?s�� ���:O�r�_m�@.�������$1���`]�w��'��<,���3�P��
��6�ׁ�?�K(�      Q   S   x�3��IM����O�LUHI�Qp+-)-��2��!c�ih�����������������X\���e�ih�CP��	�=... )+�      J   U   x�����K�/�M,�LN�4�D�q�sr:'�$g���@��06P�	gXfbzz�B^jNN��{bNbqq&��=... ��!      O   l   x��1�0���>����wb��J]�l)�����G������	!�1k�ȱ���zjQ4H��ۚ-���n�z�;^KN��G��l��i;j�z5�wGD�_�      H   �   x��л�0��9y��@��zIW�1��*Kn,���ԝ����G�-}x�a� �(�jek�v�����UΩ��I�ڮ��aAVZ�cHL<a�ޑ�K���u?�]��R���P���>�,�x+ӄU�DP]�`]�h�]./Ą�� ρ�Xh�P<(�J�_�����FJ�u�ee      M   �   x���A
�0@���)r���d�4ݺ/���P��l��
�@��~�	سt>t>�Di"�3G��ꬾM���h��n6+b3�:�)�0�'-�5 �������8�W�e1W泌;k�u5�I$%�[����Nm      F   �   x�-��n�0Eg�+�H=,yL�.n$E�.�+ +����"��ᥰ���e.pϋ�d�L�H�^�A:�Nk��T�4xb!ac��C�S�\�V����I<1��Y#p^��t\�!�aܷ��פpɺL�f�ĖL�=�|�ל��ڀH�v=��0���<7����{B�^L:c      K      x��A
�@E��)rqԅ.�SPt�&������z}������C����!lԥ�OӁ�դ�WмP	��o�f���J6�ظ�>g.��[�S��2eٸ���N��m���i���|v ���-�     