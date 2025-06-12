import React from "react";
import "../css/Container.css";

export default function Container({ children, className = "" }) {
  return (
    <div className={`${className}`}>
      {children}
    </div>
  );
}
