-- Use the schema roll_the_table
USE roll_the_table;

INSERT INTO product (
    name, purchase_price, iva, sale_price, images_path,
    description, vendor, stock_quantity, discount, category,
    minimum_age, barcode, sale_status
) VALUES
      (
          'Gioco da Tavolo - Avalon',
          20.00, 22.00, 24.40,
          'images/products/avalon.jpg',
          'Un gioco di deduzione sociale ambientato nel mondo di Re Artù.',
          'Fantasy Games Inc.',
          50, 10.00, 'Party Game',
          13, '1234567890123', 'on_sale'
      ),
      (
          'Gioco di Carte - Uno',
          5.00, 22.00, 6.10,
          'images/products/uno.jpg',
          'Il classico gioco di carte per tutta la famiglia.',
          'Mattel',
          100, 0.00, 'Family',
          6, '2345678901234', 'on_sale'
      ),
      (
          'Scacchi Deluxe in Legno',
          30.00, 22.00, 36.60,
          'images/products/chess.jpg',
          'Set di scacchi di alta qualità in legno con finiture artigianali.',
          'WoodWorks',
          25, 5.00, 'Classico',
          8, '3456789012345', 'on_sale'
      ),
      (
          'Gioco Strategico - Catan',
          25.00, 22.00, 30.50,
          'images/products/catan.jpg',
          'Colonia, commercio e sviluppo in un gioco strategico iconico.',
          'Kosmos',
          10, 0.00, 'Strategia',
          10, '4567890123456', 'on_sale'
      ),
      (
          'Gioco per Bambini - Memory Animali',
          8.00, 22.00, 9.76,
          'images/products/memory_animals.jpg',
          'Gioco di memoria con simpatici animali, adatto ai più piccoli.',
          'BabyPlay',
          80, 0.00, 'Educativo',
          3, '5678901234567', 'on_sale'
      ),
      (
          'Gioco di Carte - Dixit',
          18.00, 22.00, 21.96,
          'images/products/dixit.jpg',
          'Un gioco narrativo con carte illustrate per stimolare l\'immaginazione.',
          'Libellud',
          40, 0.00, 'Party Game',
          8, '6789012345678', 'on_sale'
      ),
      (
          'Puzzle 1000 Pezzi - Mappa del Mondo',
          12.00, 22.00, 14.64,
          'images/products/puzzle_world.jpg',
          'Puzzle da 1000 pezzi raffigurante una mappa antica del mondo.',
          'PuzzleMania',
          60, 0.00, 'Puzzle',
          10, '7890123456789', 'on_sale'
      ),
      (
          'Gioco di Carte - Exploding Kittens',
          15.00, 22.00, 18.30,
          'images/products/exploding_kittens.jpg',
          'Divertente gioco di carte strategico con gattini esplosivi.',
          'Exploding Kittens LLC',
          50, 5.00, 'Family',
          7, '8901234567890', 'on_sale'
      ),
      (
          'Gioco di Strategia - Carcassonne',
          20.00, 22.00, 24.40,
          'images/products/carcassonne.jpg',
          'Costruisci paesaggi medievali piazzando tessere.',
          'Hans im Glück',
          35, 0.00, 'Strategia',
          8, '9012345678901', 'on_sale'
      ),
      (
          'Gioco da Tavolo - Ticket to Ride',
          28.00, 22.00, 34.16,
          'images/products/ticket_to_ride.jpg',
          'Costruisci la tua rete ferroviaria per conquistare l\'America.',
          'Days of Wonder',
          20, 0.00, 'Family',
          8, '0123456789012', 'on_sale'
      ),
      (
          'Gioco di Ruolo - D&D Starter Set',
          22.00, 22.00, 26.84,
          'images/products/dnd_starter.jpg',
          'Il set introduttivo di Dungeons & Dragons.',
          'Wizards of the Coast',
          15, 0.00, 'Roleplay',
          12, '1123456789012', 'on_sale'
      ),
      (
          'Gioco da Tavolo - Risiko!',
          27.00, 22.00, 32.94,
          'images/products/risiko.jpg',
          'Il classico gioco di conquista territoriale.',
          'Spin Master',
          25, 0.00, 'Strategia',
          10, '1223456789012', 'on_sale'
      ),
      (
          'Gioco di Carte - Dobble',
          9.00, 22.00, 10.98,
          'images/products/dobble.jpg',
          'Riconosci le immagini più velocemente degli avversari.',
          'Asmodee',
          70, 0.00, 'Family',
          6, '1323456789012', 'on_sale'
      ),
      (
          'Puzzle 500 Pezzi - Castello',
          7.00, 22.00, 8.54,
          'images/products/puzzle_castle.jpg',
          'Puzzle da 500 pezzi di un suggestivo castello medievale.',
          'PuzzleMania',
          45, 0.00, 'Puzzle',
          8, '1423456789012', 'on_sale'
      ),
      (
          'Gioco da Tavolo - Monopoly',
          25.00, 22.00, 30.50,
          'images/products/monopoly.jpg',
          'L\'iconico gioco di contrattazione immobiliare.',
          'Hasbro',
          30, 0.00, 'Family',
          8, '1523456789012', 'on_sale'
      ),
      (
          'Gioco per Bambini - Ludo',
          6.00, 22.00, 7.32,
          'images/products/ludo.jpg',
          'Un classico gioco di percorso per i più piccoli.',
          'KidsFun',
          80, 0.00, 'Family',
          5, '1623456789012', 'on_sale'
      ),
      (
          'Gioco Educativo - Tabelline',
          4.00, 22.00, 4.88,
          'images/products/tabelline.jpg',
          'Carte per imparare le tabelline in modo divertente.',
          'EduGames',
          90, 0.00, 'Educativo',
          6, '1723456789012', 'on_sale'
      ),
      (
          'Gioco da Tavolo - Pandemic',
          30.00, 22.00, 36.60,
          'images/products/pandemic.jpg',
          'Collabora per salvare il mondo da epidemie letali.',
          'Z-Man Games',
          20, 0.00, 'Cooperativo',
          12, '1823456789012', 'on_sale'
      ),
      (
          'Puzzle 1500 Pezzi - Notte Stellata',
          15.00, 22.00, 18.30,
          'images/products/puzzle_starry_night.jpg',
          'Puzzle di Van Gogh da 1500 pezzi.',
          'PuzzleArt',
          25, 0.00, 'Puzzle',
          10, '1923456789012', 'on_sale'
      ),
      (
          'Gioco di Carte - Cards Against Humanity',
          20.00, 22.00, 24.40,
          'images/products/cards_against_humanity.jpg',
          'Gioco di carte per adulti dal sarcasmo esilarante.',
          'CAH LLC',
          40, 0.00, 'Party Game',
          18, '2023456789012', 'on_sale'
      ),
      (
          'Gioco da Tavolo - Cluedo',
          24.00, 22.00, 29.28,
          'images/products/cluedo.jpg',
          'Risolvi il mistero di un omicidio.',
          'Hasbro',
          35, 0.00, 'Investigativo',
          8, '2123456789012', 'on_sale'
      ),
      (
          'Gioco di Carte - Saboteur',
          10.00, 22.00, 12.20,
          'images/products/saboteur.jpg',
          'Scava tunnel ma attenzione ai sabotatori!',
          'AMIGO',
          50, 0.00, 'Party Game',
          8, '2223456789012', 'on_sale'
      ),
      (
          'Gioco di Strategia - 7 Wonders',
          28.00, 22.00, 34.16,
          'images/products/7wonders.jpg',
          'Costruisci la tua civiltà attraverso le ere.',
          'Repos Production',
          20, 0.00, 'Strategia',
          10, '2323456789012', 'on_sale'
      ),
      (
          'Gioco da Tavolo - Azul',
          22.00, 22.00, 26.84,
          'images/products/azul.jpg',
          'Crea mosaici con piastrelle colorate.',
          'Next Move Games',
          30, 0.00, 'Astratto',
          8, '2423456789012', 'on_sale'
      ),
      (
          'Gioco per Bambini - Domino Animali',
          5.00, 22.00, 6.10,
          'images/products/domino_animals.jpg',
          'Domino illustrato per i più piccoli.',
          'BabyPlay',
          60, 0.00, 'Educativo',
          3, '2523456789012', 'on_sale'
      ),
      (
          'Gioco da Tavolo - Small World',
          26.00, 22.00, 31.72,
          'images/products/small_world.jpg',
          'Conquista territori con razze fantastiche.',
          'Days of Wonder',
          15, 0.00, 'Strategia',
          10, '2623456789012', 'on_sale'
      ),
      (
          'Gioco di Ruolo - Pathfinder Starter',
          20.00, 22.00, 24.40,
          'images/products/pathfinder_starter.jpg',
          'Kit introduttivo per Pathfinder GdR.',
          'Paizo',
          12, 0.00, 'Roleplay',
          12, '2723456789012', 'on_sale'
      ),
      (
          'Gioco da Tavolo - Agricola',
          32.00, 22.00, 39.04,
          'images/products/agricola.jpg',
          'Gestisci la tua fattoria e la tua famiglia.',
          'Lookout Games',
          10, 0.00, 'Strategia',
          12, '2823456789012', 'on_sale'
      ),
      (
          'Gioco da Tavolo - Splendor',
          19.00, 22.00, 23.18,
          'images/products/splendor.jpg',
          'Acquista gemme e carte per diventare mercante.',
          'Space Cowboys',
          30, 0.00, 'Strategia',
          10, '2923456789012', 'on_sale'
      ),
      (
          'Gioco di Carte - Bang!',
          14.00, 22.00, 17.08,
          'images/products/bang.jpg',
          'Sparatorie nel Far West tra sceriffi e fuorilegge.',
          'dV Giochi',
          40, 0.00, 'Party Game',
          8, '3023456789012', 'on_sale'
      );

# TODO: remove as soon as possible
-- Insert temporary admin credentials
INSERT INTO admin (username, email, psw_hash) VALUES ('admin', 'admin@email.com', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918');