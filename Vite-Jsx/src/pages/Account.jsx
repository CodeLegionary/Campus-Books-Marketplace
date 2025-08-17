const Account = ({ user }) => {
  // Generate initials from the name
  const getInitials = (name) => {
    return name
      .split(' ')
      .map((n) => n[0]?.toUpperCase())
      .join('');
  };

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
          {getInitials('user name')}
        </div>
      </div>

        <div>
          <h2>user name: </h2>
          <h3>User INFO</h3>
          <p>Stato Utente: Attivo</p>
          <p>Ruolo Utente: USER</p>
          <p>Riportato: y</p>
        </div>
      

      <div style={{ marginTop: '30px' }}>
        <h3>📧 Email di Registrazione</h3>
        <p>user email</p>
      </div>
    </div>
  );
};

export default Account;