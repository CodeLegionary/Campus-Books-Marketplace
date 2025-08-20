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
            <button
              onClick={() => onSimpleReport(book.ownerId, book.id)}
              style={{
                backgroundColor: '#ffc107',
                color: 'black',
                border: 'none',
                padding: '5px 10px',
                borderRadius: '3px',
                cursor: 'pointer',
                marginRight: '10px',
                fontSize: '0.8em',
                transition: 'background-color 0.2s ease',
              }}
            >
              Segnala Utente
            </button>
          )}

          {onBuyBook && ( // Only show buy button if the function is provided
            <button
              onClick={() => onBuyBook(book.id, book.ownerId)} // Pass book ID and owner ID
              style={{
                backgroundColor: '#28a745', // Green color
                color: 'white',
                border: 'none',
                padding: '5px 10px',
                borderRadius: '3px',
                cursor: 'pointer',
                marginRight: '10px',
                fontSize: '0.8em',
                transition: 'background-color 0.2s ease',
              }}
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
