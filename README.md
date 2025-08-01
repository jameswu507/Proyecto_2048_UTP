# 2048 Futurista – Proyecto Final UTP

## Descripción

Este proyecto es una versión mejorada y personalizada del juego **2048**, desarrollado como parte del **Proyecto Final** de la Universidad Tecnológica de Panamá (UTP), Facultad de Ingeniería de Sistemas Computacionales.  
La aplicación presenta un **diseño futurista y animado**, con efectos visuales y sonoros innovadores que mejoran la experiencia del usuario.

---

##  Características principales

- **Interfaz futurista** con fondo animado y diseño moderno.  
- **Música de fondo (La Pantera Rosa)** con control de volumen dentro del juego.  
- **Efectos de sonido futuristas** al mover fichas y mensajes motivadores (Good, Very Good).  
- **Animaciones con robots, vehículos y máquinas** en las fichas al fusionarse.  
- **Animación especial “WASTED” con sonido GTA V** cuando el jugador pierde.  
- **Controles mediante teclas (W, A, S, D)** o flechas del teclado.  
- **Visualización de **Puntuación actual y Mejor puntuación**.  
- Botón de **Reiniciar juego** para empezar una nueva partida.  
- **Validaciones de entrada y manejo de excepciones** para evitar errores inesperados.

---

## Tecnologías utilizadas

- **Lenguaje:** Java 17+  
- **IDE:** IntelliJ IDEA  
- **Bibliotecas:** Swing (Javax) para GUI  
- **Gestión de recursos:** Carpeta `recursos/` para imágenes, GIFs y audios  
- **Control de versiones:** Git y GitHub

---

## Estructura del proyecto

```
Proyecto_2048_UTP/
│── src/
│   ├── modelo/         # Lógica del juego (Tablero, Ficha, GeneradorAleatorio)
│   ├── control/        # Controlador del juego y AudioManager
│   ├── vista/          # GUI2048 y Main (Pantalla de inicio)
│── recursos/
│   ├── audio/          # Música y efectos de sonido
│   ├── imagenes/       # Logos UTP, FISC, fondo animado, robots, wasted
│── README.md
```

---

## Cómo jugar

1. **Clonar el repositorio:**

```bash
git clone https://github.com/jameswu507/Proyecto_2048_UTP.git
```

2. **Abrir en IntelliJ IDEA**  
   - Ir a `File > Open` y seleccionar la carpeta del proyecto.

3. **Configurar recursos:**  
   - Asegúrate de que la carpeta `recursos` esté marcada como **Resources Root** en IntelliJ (`clic derecho > Mark Directory as > Resources Root`).

4. **Ejecutar el juego:**  
   - Ejecutar `Main.java` para abrir la pantalla de inicio.  
   - Presionar **"Iniciar Juego"**para comenzar.

5. **Controles:**  
   - Usa **W, A, S, D** o **flechas** para mover las fichas.  
   - Junta fichas iguales para sumar puntos.  
   - Al llegar a 2048 ganas la partida.

##  Integrantes

- Jaime Wu  
- Andrés Wu  
- Michael Chen  
- YongSheng Du  

Profesor: **Rodrigo Yángüez**  
Universidad Tecnológica de Panamá – **Facultad de Ingeniería de Sistemas Computacionales**

---
