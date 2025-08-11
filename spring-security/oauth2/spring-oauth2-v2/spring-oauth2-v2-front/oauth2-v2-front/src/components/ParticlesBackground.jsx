import React, { useCallback } from 'react';
import Particles from 'react-tsparticles';
import { loadFull } from 'tsparticles';
import '../css/ParticlesBackground.css';

export default function ParticlesBackground() {
  const customInit = useCallback(async (engine) => {
    await loadFull(engine);
  }, []);

  const options={
    fullScreen: true,
    background: { color: '#e0f7fa' },
    particles: {
      number: {
        value: 50,
        density: { enable: true, area: 800 }
      },
      shape: {
        type: "circle" // puntos en vértices
      },
      size: {
        value: 4
      },
      opacity: {
        value: 0.7
      },
      move: {
        enable: true,
        speed: 0.5,
        direction: "none",
        random: true,
        straight: false,
        outModes: "out"
      },
      color: {
        value: "#0a58ca" // azul medio
      },
      links: {
        enable: true,
        color: "#0a58ca",
        distance: 130,
        opacity: 0.4,
        width: 3
      }
    }
  };
  
  return (
    <div className="particles-container">
      <Particles
        init={customInit}
        options={options}
      />
    </div>
  );
}
