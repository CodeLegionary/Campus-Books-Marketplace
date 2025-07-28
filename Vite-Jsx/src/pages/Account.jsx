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
      <header style={{ display: 'flex', alignItems: 'center', gap: '20px' }}>
        {/* Fallback avatar using initials */}
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

        <div><h1 className='flag'>Il tuo Account</h1>
          <h2>user name</h2>
          <p>user description</p>
        </div>
      </header>

      <section style={{ marginTop: '30px' }}>
        <h3>📧 Email</h3>
        <p> user email</p>
      </section>

      <section style={{ marginTop: '30px' }}>
        <h3>📚 Libri</h3>
        <div>
          <p><strong>Inseriti:</strong> { 'Nessuno'}</p>
          <p><strong>Venduti:</strong> { 'Nessuno'}</p>
          <p><strong>Acquistati:</strong> { 'Nessuno'}</p>
        </div>
      </section>
    </div>
  );
};

export default Account;