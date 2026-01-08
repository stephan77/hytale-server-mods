# TransferAPI – Analyse

## Quelle
- Artefakt: com.shailist.hytale:transfer-api
- Version: 0.1.0-SNAPSHOT
- Quelle: Artifactory (snapshots)
- Stand: <Datum>

---

## Zweck (erste Einschätzung)
Vermuteter Zweck der API:

- [ ] Spielertransfer zwischen Servern
- [ ] Server-/Shard-Orchestrierung
- [ ] Infrastruktur / Low-Level
- [ ] Nicht primär Gameplay-bezogen

Kurzfazit:
> TransferAPI ist vermutlich <XYZ>.

---

## Struktur
### Packages
- `...`
- `...`

### Öffentliche Entry Points
- Klasse/Interface: `<Name>`
- Verantwortung: `<kurz>`

---

## Transfer-Modell
- Zieldefinition erfolgt über: <ID / Name / Objekt>
- Transfer ist: <sync / async>
- Ergebnis ist: <void / Result / Future>

---

## Fehler- & Statusmodell
- Mögliche Fehler:
  - <timeout?>
  - <denied?>
- Werden Fehler:
  - geworfen
  - zurückgegeben
  - geloggt

---

## Erkenntnisse über Server-Architektur
- Server sind vermutlich:
  - [ ] zustandslos
  - [ ] zustandsbehaftet
- Transfers bedeuten:
  - [ ] Session-Wechsel
  - [ ] Neuverbindung
  - [ ] Kontext-Migration

---

## Abgrenzung
**Nicht sichtbar / nicht enthalten:**
- Gameplay-Events
- World-/Block-Logik
- Commands
- Permissions

---

## Offene Fragen
- Wie wird Transfer ausgelöst?
- Wer entscheidet über Erlaubnis?
- Wie wird State behandelt?

---

## Bewertung
- Relevanz für Modding: niedrig / mittel / hoch
- Relevanz für Server-Netzwerke: hoch
- Einsatzempfehlung aktuell: Analyse & Vorbereitung

### Storage API Erkenntnisse

- TransferAPI ist primär eine generische Ressourcen-Transfer-API
- Kein Spieler-/Server-Transfer
- Stark transaktional (ACID)
- Ressourcenmodell basiert auf immutable Variants
- Fundament für Inventare, Maschinen, Automation
- Nicht direkt Teil der Gameplay-Modding-API
