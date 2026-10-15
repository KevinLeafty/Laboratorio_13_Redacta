# Práctica 13 — Redacta

Código de arranque de la app de la Práctica 13 de TC2007B.

Redacta reescribe tus textos con Gemma 3 1B, un modelo de lenguaje que corre en
el CPU del teléfono: sin internet y sin que lo que escribes salga del dispositivo.
Este repo trae las pantallas ya dibujadas con el sistema de diseño de la
Práctica 7, pero sin nada detrás: hoy les escribes la carga del modelo, la
respuesta en streaming, la forma de pedirle las cosas y sus pruebas.

## Antes de clase

1. Descarga el modelo `gemma3-1b-it-int4.litertlm` (584 MB) de
   <https://huggingface.co/litert-community/Gemma3-1B-IT> (con tu cuenta, después de
   aceptar la licencia de Gemma; los pasos están en la guía). No lo pongas en esta
   carpeta: no va al repo (el `.gitignore` ya lo excluye).
2. Revisa que tu emulador tenga al menos 1.5 GB libres y, de preferencia, 4 GB de RAM.

## Cómo empezar

1. Clona el repositorio y ábrelo en Android Studio.
2. Corre la app una vez: se ve completa, con «Cargando el modelo…».
3. Sigue la guía: <https://startdroid.com/practicas/redacta.html>

## Cómo trabajar

Haz un commit en cada checkpoint de la guía:

```bash
git add -A ; git commit -m "checkpoint a3"
```

El experimento del Bloque D va en la rama `experimento-hilo`, con commit en la
rama antes de volver a `main`.

## Uso de IA

Todo commit con código generado por IA debe declararlo con un trailer
`Co-Authored-By`. Ver la política completa en la guía.

## Entrega

Ver la rúbrica en la guía. Sube todas las ramas: `git push origin --all`.
