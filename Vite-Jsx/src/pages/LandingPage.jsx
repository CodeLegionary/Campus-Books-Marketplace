// src/pages/LandingPage.jsx
import { useEffect, useState } from "react";
import BookCard from "../components/BookCard"; // Adjust import path for BookCard

const API_BASE_URL = import.meta.env.VITE_APP_API_BASE_URL || "http://localhost:8080";

const LandingPage = () => { // LandingPage no longer accepts adminEmail prop for now
  const [books, setBooks] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [maxPrice, setMaxPrice] = useState("");
  const [bookCondition, setBookCondition] = useState("");
  const [newBook, setNewBook] = useState({ title: "", description: "", price: "", pariANuovo: "" });
  const [fetchError, setFetchError] = useState(null);

  useEffect(() => {
    const fetchBooks = async () => {
      try {
        const response = await fetch(`${API_BASE_URL}/api/books`, {
          credentials: 'include',
        });

        if (!response.ok) {
          const errorText = await response.text();
          throw new Error(`HTTP error! Status: ${response.status} - ${errorText || response.statusText}`);
        }

        const data = await response.json();

        if (Array.isArray(data)) {
          setBooks(data);
          setFetchError(null);
        } else {
          console.warn("API '/api/books' did not return an array. Received:", data);
          setBooks([]);
          setFetchError("Received unexpected data format from server. Expected an array of books.");
        }
      } catch (error) {
        console.error("Error fetching books:", error);
        setBooks([]);
        setFetchError(`Failed to load books: ${error.message}`);
      }
    };

    fetchBooks();
  }, []);

  const filteredBooks = books.filter(book =>
    book.title.toLowerCase().includes(searchTerm.toLowerCase()) &&
    (!maxPrice || book.price <= parseFloat(maxPrice)) &&
    (!bookCondition || (bookCondition === "Nuovo" && book.pariANuovo) || (bookCondition === "Usato" && !book.pariANuovo))
  );

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const bookToSend = {
        ...newBook,
        pariANuovo: newBook.pariANuovo === "Nuovo",
      };

      const res = await fetch(`${API_BASE_URL}/api/books`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(bookToSend),
        credentials: 'include',
      });

      if (!res.ok) {
        const errorText = await res.text();
        throw new Error(`Failed to publish book: ${res.status} - ${errorText || res.statusText}`);
      }

      const updatedBook = await res.json();
      alert("Libro pubblicato!");
      setBooks(prev => [...prev, updatedBook]);
      setNewBook({ title: "", description: "", price: "", pariANuovo: "" });
    } catch (error) {
      console.error("Error publishing book:", error);
      alert(`Errore durante la pubblicazione del libro: ${error.message}`);
    }
  };

  // Simplified handleSimpleReport: only triggers API call, no email
  const handleSimpleReport = async (reportedUserId, reportedBookId) => {
    if (!window.confirm("Sei sicuro di voler segnalare questo utente per questo annuncio?")) {
        return;
    }

    try {
      const reportPayload = {
        reportedUserId: reportedUserId,
        reportedBookId: reportedBookId,
        // Potentially add more details like 'reason', 'timestamp', etc., on the backend
      };

      const response = await fetch(`${API_BASE_URL}/api/reports/simple`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(reportPayload),
        credentials: "include",
      });

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`Failed to submit report: ${response.status} - ${errorText || response.statusText}`);
      }

      const responseData = await response.json();
      alert(responseData.message || "Segnalazione inviata con successo!");

    } catch (error) {
      console.error("Error submitting report:", error);
      alert(`Errore durante l'invio della segnalazione: ${error.message}`);
    }
  };

  // Simplified handleBuyBook: only triggers an alert for now, no email logic
  const handleBuyBook = (bookId, ownerId) => {
    // We're just showing an alert for now.
    // When you're ready, this is where you'd add the logic to fetch owner email,
    // open mailto, or initiate a chat, etc.
    alert(`Hai cliccato "Compra" per il libro ID: ${bookId} (Proprietario ID: ${ownerId}). Per ora, questa è una simulazione.`);
    console.log(`Simulando l'acquisto per il libro ID: ${bookId}, proprietario ID: ${ownerId}`);
  };


  return (
    <div className="landing-page">
      <header>
        <div className="wrapper">
          <h1 className="flag">Benvenut@ allo Store!</h1>
        </div>
        <p>Scambia, compra o vendi i tuoi libri con altri studenti.</p>
      </header>

      <section className="filters">
        <input placeholder="🔍 Cerca titolo..." value={searchTerm} onChange={(e) => setSearchTerm(e.target.value)} />
        <input type="number" placeholder="Prezzo massimo (€)" value={maxPrice} onChange={(e) => setMaxPrice(e.target.value)} />
        <select value={bookCondition} onChange={(e) => setBookCondition(e.target.value)}>
          <option value="">Condizione</option>
          <option value="Nuovo">Nuovo</option>
          <option value="Usato">Usato</option>
        </select>
      </section>

      {fetchError && <div style={{ color: 'red', padding: '10px', border: '1px solid red', borderRadius: '5px', margin: '10px 0' }}>{fetchError}</div>}

      <section className="book-links" id="links">
        {filteredBooks.length > 0 ? (
          filteredBooks.map(book => (
            <BookCard
              key={book.id}
              book={book}
              onSimpleReport={handleSimpleReport} // Pass the report function
              onBuyBook={handleBuyBook} // Pass the buy function
              // No adminEmail prop passed to BookCard anymore
            />
          ))
        ) : (
          <p>{!fetchError ? "Nessun libro trovato o in caricamento..." : ""}</p>
        )}
      </section>

      <section className="submission-form">
        <h2>📥 Inserisci un libro da vendere</h2>
        <form onSubmit={handleSubmit}>
          <input placeholder="Titolo" required value={newBook.title} onChange={e => setNewBook({ ...newBook, title: e.target.value })} />
          <textarea placeholder="Descrizione" required value={newBook.description} onChange={e => setNewBook({ ...newBook, description: e.target.value })} />
          <input type="number" placeholder="Prezzo (€)" required value={newBook.price} onChange={e => setNewBook({ ...newBook, price: e.target.value })} />
          <select required value={newBook.pariANuovo} onChange={e => setNewBook({ ...newBook, pariANuovo: e.target.value })}>
            <option value="">Condizione</option>
            <option value="Nuovo">Nuovo</option>
            <option value="Usato">Usato</option>
          </select>
          <button type="submit">Pubblica!</button>
        </form>
      </section>
    </div>
  );
};

export default LandingPage; // Export LandingPage