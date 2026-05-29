#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
SVG="${ROOT_DIR}/gfx/app-icon.svg"
RES_DIR="${ROOT_DIR}/app/src/main/res"
FOREGROUND="${RES_DIR}/drawable/ic_launcher_foreground.webp"

if ! command -v convert >/dev/null 2>&1; then
  echo "error: ImageMagick 'convert' was not found" >&2
  exit 1
fi

if [[ ! -f "${SVG}" ]]; then
  echo "error: missing ${SVG}" >&2
  exit 1
fi

render_webp() {
  local size="$1"
  local out="$2"
  convert -background none "${SVG}" -resize "${size}x${size}" "${out}"
}

render_round_webp() {
  local size="$1"
  local out="$2"
  convert -background none "${SVG}" -resize "${size}x${size}" -alpha set \
    \( -size "${size}x${size}" xc:none -fill white -draw "circle $((size / 2)),$((size / 2)) $((size / 2)),0" \) \
    -compose copyopacity -composite "${out}"
}

render_webp 432 "${FOREGROUND}"

render_webp 48 "${RES_DIR}/mipmap-mdpi/ic_launcher.webp"
render_webp 72 "${RES_DIR}/mipmap-hdpi/ic_launcher.webp"
render_webp 96 "${RES_DIR}/mipmap-xhdpi/ic_launcher.webp"
render_webp 144 "${RES_DIR}/mipmap-xxhdpi/ic_launcher.webp"
render_webp 192 "${RES_DIR}/mipmap-xxxhdpi/ic_launcher.webp"

render_round_webp 48 "${RES_DIR}/mipmap-mdpi/ic_launcher_round.webp"
render_round_webp 72 "${RES_DIR}/mipmap-hdpi/ic_launcher_round.webp"
render_round_webp 96 "${RES_DIR}/mipmap-xhdpi/ic_launcher_round.webp"
render_round_webp 144 "${RES_DIR}/mipmap-xxhdpi/ic_launcher_round.webp"
render_round_webp 192 "${RES_DIR}/mipmap-xxxhdpi/ic_launcher_round.webp"

echo "Rendered launcher icons from ${SVG}"

if [[ "${1:-}" == "--build" ]]; then
  cd "${ROOT_DIR}"
  ./gradlew assembleDebug
fi
