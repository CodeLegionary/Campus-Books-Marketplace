import './BookCard.css';

const BookCard = ({ book, onSimpleReport, onBuyBook }) => { // Only accept necessary props
  return (
    <div className="book-card">
      <h3>{book.title}</h3>
      <p><strong>Info:</strong> {book.description}</p>
      <p><strong>Prezzo:</strong> €{book.price}</p>
      <p><strong>Condizione:</strong> {book.pariANuovo ? "Nuovo" : "Usato"}</p>
      {book.ownerId && ( // Only show buttons if there's an owner
        <p>
          {onSimpleReport && ( // Only show report button if the function is provided
            <button className="report-button"
              onClick={() => onSimpleReport(book.ownerId, book.id)}
            >
              Segnala Utente
            </button>
          )}

          {onBuyBook && ( // Only show buy button if the function is provided
            <button className="buy-button"
              onClick={() => onBuyBook(book.id, book.ownerId)} // Pass book ID and owner ID
            >
              Compra
            </button>
          )}

        </p>
      )}
    </div>
  );
};

export default BookCard;
