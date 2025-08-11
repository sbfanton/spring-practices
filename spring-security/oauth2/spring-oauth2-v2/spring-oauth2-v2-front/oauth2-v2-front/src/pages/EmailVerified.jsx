import React, { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheckCircle, faExclamationTriangle } from "@fortawesome/free-solid-svg-icons";
import Container from '../components/Container';
import "../css/EmailVerified.css";

const EmailVerified = () => {
  const [status, setStatus] = useState("loading"); // "loading" | "success" | "error"

  useEffect(() => {
    fetch("http://localhost:8080/mails/email-validated-status", {
      credentials: "include",
    })
        .then((res) => {
          if (res.ok) {
            setStatus("success");
          } else {
            setStatus("error");
          }
        })
        .catch(() => setStatus("error"));
  }, []);

  // Cargando...
  if (status === "loading") {
    return (
        <Container className='container-email-verified'>
          <div className="email-verified-msg">Verificando email...</div>
        </Container>
    );
  }

  // Verificación exitosa
  if (status === "success") {
    return (
        <Container className='container-email-verified'>
          <FontAwesomeIcon
                  icon={faCheckCircle}
                  className="success-icon"
              />
              <h1 className="email-verified-title">¡Email verificado!</h1>
              <p className="email-verified-msg">
                Tu dirección de correo electrónico fue verificada con éxito.
              </p>
              <a
                  href="/login"
                  className="login-button"
              >
                Iniciar sesión
              </a>
        </Container>
    );
  }

  // Verificación fallida
  return (
      <Container className='container-email-verified'>
        <FontAwesomeIcon
                icon={faExclamationTriangle}
                className="error-icon"
            />
            <h1 className="email-verified-title">Error de verificación</h1>
            <p className="email-verified-msg">
              El enlace es inválido o la verificación ya fue usada.
            </p>
      </Container>
  );
};

export default EmailVerified;