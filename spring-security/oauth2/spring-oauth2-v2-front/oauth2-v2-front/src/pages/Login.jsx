import React from 'react';

export default function Login() {
  const handleLoginGithub = () => {
    // Redirige al backend para iniciar OAuth2 con GitHub
    window.location.href = 'http://localhost:8080/oauth2/login?provider=github';
  };

  return (
    <div style={{ textAlign: 'center', marginTop: '3rem' }}>
      <h1>Login</h1>
      <button
        onClick={handleLoginGithub}
        style={{
          padding: '0.75rem 1.5rem',
          fontSize: '1.1rem',
          cursor: 'pointer',
          display: 'inline-flex',
          alignItems: 'center',
          gap: '0.5rem',
          borderRadius: '6px',
          border: '1px solid #333',
          backgroundColor: '#fff',
        }}
      >
        {/* Ícono simple de GitHub SVG */}
        <svg
          height="24"
          width="24"
          viewBox="0 0 16 16"
          fill="currentColor"
          xmlns="http://www.w3.org/2000/svg"
          aria-hidden="true"
        >
          <path d="M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38
           0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52
           -.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2
           -3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.01.08-2.11 0 0 .67-.21
           2.2.82a7.548 7.548 0 012 0c1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.91.08 2.11.51.56.82
           1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01
           2.2 0 .21.15.46.55.38A8.013 8.013 0 0016 8c0-4.42-3.58-8-8-8z" />
        </svg>
        Iniciar sesión con GitHub
      </button>
    </div>
  );
}
