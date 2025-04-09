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
      );
