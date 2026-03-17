------
## Taller 2 - Arquitectura MVVM



### Respuestas a Preguntas Conceptuales

#### 1. ¿Qué problema resuelve el ViewModel en Android?
El ViewModel evita que los datos de la UI se pierdan cuando ocurre un cambio de configuración (como rotar la pantalla). Además, separa la lógica de la interfaz, haciendo el código más organizado y fácil de mantener.

#### 2. ¿Por qué LiveData es "lifecycle-aware" y qué beneficio trae?
LiveData es "lifecycle-aware" porque solo actualiza la UI cuando el componente (Activity o Fragment) está activo. Esto evita errores, fugas de memoria y actualizaciones innecesarias cuando la pantalla no está visible

#### 3. Explica con tus propias palabras el flujo de datos en MVVM
En MVVM, la vista (UI) observa los datos del ViewModel. El ViewModel obtiene o procesa la información (por ejemplo desde un Repository) y la expone a la vista. Cuando los datos cambian, la UI se actualiza automáticamente sin necesidad de manejar todo manualmente.

#### 4. ¿Qué ventaja tiene usar Fragments vs múltiples Activities?
Los Fragments permiten reutilizar componentes de UI dentro de una misma Activity, haciendo la navegación más flexible y eficiente. Además, facilitan el manejo de interfaces adaptables y reducen la cantidad de Activities en la app.

#### 5. ¿Cómo ayuda el Repository Pattern a la arquitectura?
El Repository Pattern centraliza el acceso a los datos (API, base de datos, etc.), separándolo del ViewModel. Esto hace que el código sea más limpio, escalable y fácil de probar.
