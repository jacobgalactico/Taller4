LINK AL REPOSITORIO: https://github.com/jacobgalactico/Taller4.git

# Taller 4 - Aplicación Android con Kotlin

Este repositorio contiene una aplicación desarrollada en Kotlin usando Android Studio y Jetpack Compose. La aplicación implementa varias funcionalidades modernas como persistencia de datos, widgets dinámicos, navegación entre actividades y una lista interactiva de elementos.

---

## Descripción del Proyecto

La aplicación permite a los usuarios:
- Ver una pantalla de inicio con un saludo y un botón para navegar a la actividad principal.
- Gestionar una lista de elementos:
  - **Añadir nuevos elementos.**
  - **Eliminar elementos existentes.**
  - **Guardar los elementos de forma persistente entre sesiones.**
- Mostrar un widget en la pantalla de inicio que:
  - **Actualiza dinámicamente el total de elementos en la lista.**
  - **Muestra la última hora de actualización.**

---

## Estructura del Proyecto

### Clases Principales

#### 1. MainActivity
- **Descripción**: 
  - Esta es la actividad principal de la aplicación.
  - Muestra la pantalla de inicio con un saludo y un botón para navegar a la actividad principal.
- **Funciones principales**:
  - Renderiza la interfaz inicial.
  - Incluye el botón para navegar a la actividad principal (`MainAppActivity`).

#### 2. MainAppActivity
- **Descripción**:
  - La actividad principal donde se gestiona la lista de elementos.
  - Implementa la funcionalidad de agregar y eliminar elementos.
  - Conecta la aplicación con el widget para reflejar los cambios en tiempo real.
- **Funciones principales**:
  - Renderiza la lista de elementos.
  - Permite agregar nuevos elementos a la lista.
  - Permite eliminar elementos de la lista.
  - Guarda y carga la lista desde `SharedPreferences` para persistencia entre sesiones.

#### 3. ItemListFragment
- **Descripción**:
  - Fragmento que muestra la lista de elementos.
  - Gestiona la interacción del usuario con la lista.
- **Funciones principales**:
  - Carga y muestra los elementos desde `SharedPreferences`.
  - Guarda los elementos cuando se realizan cambios (añadir/eliminar).
  - Actualiza el widget dinámicamente al modificar la lista.

#### 4. ItemAdapter
- **Descripción**:
  - Adaptador para gestionar la lista de elementos dentro de un `RecyclerView`.
- **Funciones principales**:
  - Renderiza cada elemento de la lista.
  - Incluye un botón para eliminar elementos.

#### 5. MyAppWidgetProvider
- **Descripción**:
  - Clase encargada de gestionar el widget de la aplicación.
- **Funciones principales**:
  - Muestra el total de elementos en la lista y la última hora de actualización.
  - Actualiza el widget al añadir o eliminar elementos.
  - Implementa la funcionalidad del botón "Actualizar" en el widget para actualizar la hora manualmente.

---

## Diseño de la Aplicación

### Layouts Principales

#### 1. fragment_item_list.xml
- Contiene el diseño para mostrar la lista de elementos, el campo de texto para ingresar nuevos elementos, y el botón para añadir elementos.

#### 2. item_view.xml
- Define el diseño individual de cada elemento de la lista.
- Incluye el texto del elemento y un botón para eliminarlo.

#### 3. widget_layout.xml
- Define el diseño del widget que muestra el total de elementos y la última hora de actualización.

---
