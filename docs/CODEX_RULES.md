## Autoritativer Kontext (Pflicht)

- `docs/Context.md` ist die **einzige autoritative Quelle** für:
  - Architektur
  - API-Einordnung
  - Systemverhalten
- Keine Annahmen außerhalb dieses Dokuments treffen
- Keine stillschweigenden Ergänzungen oder Vermutungen

---

## TransferAPI & SNAPSHOT-Abhängigkeiten

- Die TransferAPI ist **keine Gameplay-Modding-API**
- SNAPSHOT-Abhängigkeiten dürfen **nicht**:
  - in Produktivcode
  - in Gameplay-Code
  - in Plugin-Implementierungen
- SNAPSHOTs dienen **nur der Analyse**, nicht der Nutzung
- Kein Code darf direkt auf `com.shailist.hytale.api.transfer.*` aufbauen

---

## Transaktionen & State-Änderungen

- Hytale ist **transaktionsgetrieben**, nicht eventgetrieben
- State-Änderungen sind:
  - gepuffert
  - nicht sofort final
- Rollback ist Default, Commit ist explizit
- Globale Effekte dürfen **erst nach OUTER_CLOSING** stattfinden
- Eigene Transaktionssysteme sind **verboten**

---

## Codex-Verhalten (verbindlich)

Codex darf:
- Architektur ableiten
- Adapter-Interfaces vorschlagen
- Dokumentation erzeugen
- Platzhalter / Stubs erstellen

Codex darf **nicht**:
- Gameplay-APIs erfinden
- Events, Commands oder Player-Objekte annehmen
- TransferAPI als Modding-API behandeln
- Implementierungen auf Basis von SNAPSHOTs generieren

---

## Implementierungs-Stopp-Regel

- Solange keine offizielle Gameplay-API existiert:
  - ❌ keine Feature-Implementierungen
  - ❌ keine echten Plugin-Systeme
  - ✅ nur Struktur, Adapter, Dokumentation, Tests ohne API-Abhängigkeit
