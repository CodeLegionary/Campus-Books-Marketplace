import { useEffect, useState } from 'react';

const Account = () => {
  const API_BASE_URL = import.meta.env.VITE_APP_API_BASE_URL || "http://localhost:8080";
  const [user, setUser] = useState(null);

  // Generate initials from the name
  const getInitials = (name) => {
    return name
      .split(' ')
      .map((n) => n[0]?.toUpperCase())
      .join('');
  };

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const response = await fetch(`${API_BASE_URL}/api/user`, {
          method: 'GET',
          credentials: 'include', // Ensures cookies/session are sent
        });
        if (!response.ok) throw new Error('Failed to fetch user');
        const data = await response.json();
        setUser(data);
      } catch (error) {
        console.error('Error fetching user:', error);
      }
    };

    fetchUser();
  }, [API_BASE_URL]);

  if (!user) {
    return <p>Effettua il login per attivare il display dei dati...</p>;
  }

  return (
    <div className="account-page" style={{ padding: '20px', maxWidth: '600px', margin: 'auto' }}>
      <header className="wrapper">
        <h1 className='flag'>Il tuo Account</h1>
      </header>

      <div className="wrapper">
        <div
          style={{
            width: '60px',
            height: '60px',
            borderRadius: '50%',
            backgroundColor: '#556cd6',
            color: 'white',
            fontWeight: 'bold',
            fontSize: '24px',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
          }}
        >
          {getInitials(user.username || '')}
        </div>
      </div>

      <div>
        <h2>user name: {user.username}</h2>
        <h3>User INFO</h3>
        <p>Stato Utente: {user.status || 'Attivo'}</p>
        <p>Ruolo Utente: {user.role || 'USER'}</p>
        <p>Riportato: {user.reported ? 'y' : 'n'}</p>
      </div>

      <div style={{ marginTop: '30px' }}>
        <h3>📧 Email di Registrazione</h3>
        <p>{user.email}</p>
      </div>
    </div>
  );
};

export default Account;
