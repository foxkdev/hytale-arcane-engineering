# Conceptos Fundamentales: Mod Arcano-Mecánico

## 1. El Flujo Arcano (Maná)

El Flujo Arcano es la forma de energía mágica que impulsa todos los componentes del mod. Es un recurso que se almacena y se transmite con una intensidad variable (similar a un fluido o voltaje).

* **Unidad Base:** Se mide en unidades de Flujo Arcano por tick (ej. UA/t).
* **Intensidad:** La cantidad de Flujo presente en un componente. Directamente determina la **potencia** o la **velocidad** de las máquinas conectadas.
* **Decaimiento:** El Flujo Arcano se debilita progresivamente al ser transmitido a través de largas distancias. Esto obliga al uso estratégico de **Relés Arcanos**.
* **Foco en el Almacenamiento:** Los componentes tienen una capacidad de almacenamiento (Capacidad Máxima).

## 2. Generación y Transmisión de Energía

Estos bloques son las fuentes y los canales para el Flujo Arcano.

### 2.1. Generadores (Fuentes)

| Bloque | Función | Interacción |
| :--- | :--- | :--- |
| **Pilar de Cristal** | **Generación Constante.** Su eficiencia depende de estar anclado a un Nexo de Maná o en un bioma con alta carga mágica. | Flujo constante y lento. |
| **Altar de Rituales** | **Generación de Ráfagas.** Consume objetos mágicos o recursos raros (el "sacrificio") para producir una **gran cantidad de Flujo concentrado** de forma intermitente. | Ideal para picos de potencia. |
| **Concentrador Elemental** | **Generación por Combustible.** Utiliza **Cristales Elementales** como combustible. | La potencia y el tipo elemental del Flujo dependen del cristal. |

### 2.2. Conductores (Canales)

| Bloque | Función | Mecánica Clave |
| :--- | :--- | :--- |
| **Canalización Arcana** | **El "Cable Mágico".** Transporta el Flujo Arcano, implementando el **Decaimiento** por distancia. | Bloque de transmisión de unidad (implementa IFlujoArcano). |
| **Relé Arcano** | **Amplificador y Repeater.** Regenera la intensidad del Flujo, contrarrestando el decaimiento. | Debe colocarse periódicamente para mantener la potencia de la señal. |

## 3. Fusión Arcano-Mecánica (Conversores)

Estos bloques permiten que el sistema mágico interactúe con el subsistema mecánico (engranajes, cintas transportadoras, etc.).

| Bloque | Conversión de Energía | Relación Flujo-Mecánica |
| :--- | :--- | :--- |
| **Núcleo de Implosión** | Flujo Arcano -> Potencia de Rotación | El Flujo Arcano entrante se traduce directamente en la **velocidad de rotación (RPM)** del eje mecánico. **¡Más Flujo = Más Velocidad!** |
| **Generador de Maná** | Potencia de Rotación -> Flujo Arcano | Convierte la energía de un eje rotatorio (ej. de una rueda hidráulica) en Flujo Arcano. |

## 4. Lógica y Control (Condicionamiento del Flujo)

Estos componentes permiten al jugador modular el Flujo Arcano para tomar decisiones automatizadas.

| Bloque | Lógica | Función Principal |
| :--- | :--- | :--- |
| **Enlazador Rúnico** | **Puertas Lógicas (AND/OR/NOT).** | Realiza operaciones lógicas basadas en la **presencia** o **ausencia** de Flujo Arcano. |
| **Piedra de Umbral** | **Comparador de Intensidad.** | Activa una salida si el Flujo entrante es **igual, mayor o menor** que un valor de intensidad predefinido (el Umbral). |
| **Espejo Temporal** | **Temporizador Mágico.** | Almacena Flujo y lo libera en una ráfaga después de un **tiempo configurable**. |
| **Matriz de Secuencia** | **Secuenciador Lógico.** | Utiliza el Flujo para ejecutar una serie predefinida de acciones en un orden específico. La **velocidad de la secuencia** es controlada por la intensidad del Flujo. |

## 5. Logística Inteligente y Clasificación de Ítems

Este subsistema utiliza el Flujo Arcano para **identificar, clasificar y mover** ítems de manera inteligente, superando la limitación de velocidad de una simple tolva. La clave es que la magia "entiende" las propiedades del objeto.

### 5.1. Sensores y Filtros

| Bloque | Conversión de Datos | Mecánica |
| :--- | :--- | :--- |
| **Lector Rúnico de Ítems** | Propiedad del Ítem -> Intensidad de Flujo | Escanea un ítem al pasar (en una cinta/tolva) y emite una señal de Flujo cuya **intensidad es proporcional a una propiedad** interna del ítem (ej. rareza, valor mágico, nivel de encantamiento). |
| **Filtro de Patrón Rúnico** | Control de Flujo -> Condición de Salida | Componente programable. Se configura con un **ítem de patrón** y un **rango de intensidad de Flujo**. Se activa solo si el Flujo recibido coincide con el valor esperado (ej. solo activar para ítems de Rareza 3). |

### 5.2. Manipulación y Almacenamiento

| Bloque | Función | Uso Principal |
| :--- | :--- | :--- |
| **Conductor de Vórtice** | **Clasificador Mágico (Switch).** | Recibe el Flujo de un **Lector Rúnico** y utiliza la intensidad de esa señal para **dirigir el ítem físico** a una de sus múltiples salidas (actuando como un switch inteligente). |
| **Extractor Arcano** | **Succión de Inventario.** | Colocado sobre un contenedor, utiliza el Flujo Arcano para **succionar ítems del inventario** a una velocidad determinada por la intensidad del Flujo inyectado. |
| **Aparato de Almacenamiento Rúnico** | **Cofre Avanzado.** | Contenedor especial con mayor capacidad y una interfaz mágica que permite a los Extractores Arcanos interactuar con él. |

---
## 6. Estructura del Código Base (Java)

El código debe estar estructurado en torno a una única interfaz de energía para garantizar la interoperabilidad.

| Componente de Código | Propósito | Bloques que lo Implementan |
| :--- | :--- | :--- |
| `IFlujoArcano.java` | **Interfaz Base.** Define la API para la energía: `inyectarFlujo()`, `extraerFlujo()`, `obtenerFlujoActual()`, etc. | Canalizaciones, Generadores, Conversores. |
| `TileEntityArcanaBase` | Clase base abstracta. Maneja el almacenamiento interno (`flujoActual`) y el *ticking* básico para componentes lógicos y de almacenamiento. | Relé Arcano, Piedra de Umbral, Espejo Temporal. |
| `TileEntityCanalizacionArcana` | Implementación de la **lógica de propagación** de Flujo a los vecinos y el cálculo del **decaimiento**. | Canalización Arcana. |
| `TileEntityPilarDeCristal` | Generador de Flujo constante, hereda la propagación de `CanalizacionArcana` y añade la lógica de generación. | Pilar de Cristal. |

---
## 7. Fusión Arcano-Mecánica (El Puente)

Esta sección detalla los componentes que permiten la conversión bidireccional entre el **Flujo Arcano** (magia) y la **Potencia de Rotación** (mecánica, similar a Create).

### 7.1. Núcleo de Implosión (Mágico -> Mecánico)

Este bloque es el consumidor principal del Flujo Arcano y el generador de movimiento para el subsistema mecánico.

* **Función:** Convierte el Flujo Arcano en Potencia de Rotación.
* **Implementación de Código:** Implementa `IFlujoArcano` (como consumidor) y una nueva interfaz (`IPotenciaRotacion`) para la salida mecánica.
* **Relación de Conversión:** La **cantidad de Flujo Arcano** extraído por *tick* determina la **velocidad de rotación (RPM)** del eje conectado.
    * Ejemplo: Extraer 10 UA/t de Flujo Arcano podría generar 32 RPM. Extraer 20 UA/t generaría 64 RPM.

### 7.2. Generador de Maná (Mecánico -> Mágico)

El componente inverso.

* **Función:** Convierte la Potencia de Rotación en Flujo Arcano.
* **Implementación de Código:** Implementa `IPotenciaRotacion` (como consumidor de rotación) e `IFlujoArcano` (para la inyección de Flujo).
* **Relación de Conversión:** La **velocidad de rotación (RPM)** del eje conectado determina la **tasa de inyección** de Flujo Arcano por *tick*.

---
## 8. Lógica Avanzada y Sensores (Decisiones Inteligentes)

Estos bloques permiten que el Flujo Arcano se utilice para tomar decisiones complejas y clasificar ítems.

### 8.1. Piedra de Umbral

El comparador de Flujo.

* **Función:** Se utiliza para filtrar o condicionar la señal del Flujo Arcano. Solo permite el paso del Flujo (o activa una señal de Redstone/Rotación) si la intensidad del Flujo entrante cumple una condición.
* **Configuración:** El jugador configura un valor de Umbral.
    * Ej. Si el Umbral es 50, solo el Flujo con intensidad > 50 pasa.
* **Uso con Logística:** Conectado a un **Lector Rúnico** para activar una máquina solo si el ítem que pasa es de una rareza o valor mínimo requerido.

### 8.2. Conductor de Vórtice

El clasificador mágico para ítems.

* **Función:** Actúa como un *switch* logístico. Dirige los ítems físicos a una de sus múltiples salidas basándose en la intensidad del Flujo Arcano que recibe.
* **Conexión Lógica:** Debe estar conectado a un **Lector Rúnico** y tener múltiples **Filtros de Patrón Rúnico** conectados para definir las reglas de desvío.

---