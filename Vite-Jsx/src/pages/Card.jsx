import { useState } from 'react';

const Card = () => {
    const API_BASE_URL = import.meta.env.VITE_APP_API_BASE_URL || "http://localhost:8080";

    const [cardDetails, setCardDetails] = useState({
        cardHolder: '', // Matches Java model field
        cardNumber: '',
        expiryDate: '', // Matches Java model field
        cvv: '',
    });
    
    // Handles changes to form input fields
    const handleChange = (e) => {
        const { name, value } = e.target;
        setCardDetails((prev) => ({
            ...prev,
            [name]: value,
        }));
    };
    
    // Handles the form submission
    const handleSubmit = async (e) => {
        e.preventDefault(); // Prevents default browser form submission and page reload

        try {
            // CORRECTED URL: Now points to /api/cards/save as defined in CardController
            const response = await fetch(`${API_BASE_URL}/api/cards/save`, {
                method: 'POST', // Use POST method to send data
                headers: {
                    'Content-Type': 'application/json', // Indicate that the body is JSON
                },
                body: JSON.stringify(cardDetails), // Convert JavaScript object to JSON string
                credentials: 'include',
            });

            // Check if the HTTP response status is OK (2xx)
            if (!response.ok) {
                // If not OK, try to read the error message from the response body
                // This is crucial for debugging server-side validation or errors
                const errorBody = await response.text(); // Read as text, as it might not be JSON
                let errorMessage = `Failed to save card details. Status: ${response.status}`;
                try {
                    // Attempt to parse as JSON if it looks like JSON
                    const errorJson = JSON.parse(errorBody);
                    if (errorJson.message) {
                        errorMessage += ` - ${errorJson.message}`;
                    } else if (errorJson.error) {
                        errorMessage += ` - ${errorJson.error}`;
                    }
                } catch (parseError) {
                    // If not JSON, just append the raw text body
                    errorMessage += ` - ${errorBody.substring(0, 100)}...`; // Truncate long messages
                }
                throw new Error(errorMessage); // Throw the detailed error
            }

            // If response is OK, parse the JSON response from the server
            // (e.g., the saved Card object or a success message)
            const data = await response.json();
            console.log('Card saved successfully:', data);
            alert('Dettagli della carta inviati con successo!'); // User feedback

        } catch (error) {
            console.error('Error submitting card details:', error); // <-- Keep this, it logs the whole error object
            // Also, explicitly log the error response if available from the server
            if (error.response) {
                console.error('Server error response:', error.response);
            }
    alert(`Errore durante l'invio dei dettagli della carta: ${error.message || 'Errore sconosciuto'}`);
}
    };
    
    return (
        <div className="class-page">
            <header>
                <div className="wrapper">
                    <h1 className="flag">&nbsp;Dettagli Carta&nbsp;</h1>
                </div>
                <p>Salva qui la carta che userai per i tuoi acquisti o le tue vendite.</p>
            </header>

            <main>
                <section className="card-upload" style={{ padding: "20px", margin: "20px" }}>
                    <h2>Inserisci una carta</h2>
                    {/* Form with onSubmit handler to trigger handleSubmit */}
                    <form 
                        onSubmit={handleSubmit} 
                        style={{ display: "flex", flexDirection: "column", gap: "10px", maxWidth: "400px" }}>
                        <input
                            type="text"
                            name="cardHolder" // Matches Java model field 'cardHolder'
                            placeholder="Nome sulla carta"
                            value={cardDetails.cardHolder}
                            onChange={handleChange}
                            required
                        />
                        <input
                            type="text"
                            name="cardNumber"
                            placeholder="Numero della carta"
                            value={cardDetails.cardNumber}
                            onChange={handleChange}
                            required
                        />
                        <input
                            type="text"
                            name="expiryDate" // Matches Java model field 'expiryDate'
                            placeholder="MM/AA"
                            value={cardDetails.expiryDate}
                            onChange={handleChange}
                            required
                        />
                        <input
                            type="text"
                            name="cvv"
                            placeholder="CVV"
                            value={cardDetails.cvv}
                            onChange={handleChange}
                            required
                        />
                        {/* Submit button */}
                        <button type="submit">Invia Dettagli</button>
                    </form>
                </section>

                <section>
                    <p>Grazie per utilizzare il nostro sito.</p>
                </section>
            </main>
        </div>
    );
};

export default Card;
