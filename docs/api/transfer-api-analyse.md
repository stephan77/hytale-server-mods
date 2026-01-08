# TransferAPI – Analyse Hytale

## Quelle
- Artefakt: com.shailist.hytale:transfer-api
- Version: 0.1.0-SNAPSHOT
- Quelle: Artifactory (snapshots)
- Stand: 14.12.2025

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

### Transaction API Erkenntnisse

- Transaktionen sind Engine-weit, nicht Plugin-spezifisch
- Thread-lokal, verschachtelbar, ACID-ähnlich
- Rollback ist Default, Commit explizit
- Teilnehmer müssen Snapshot/Commit selbst handhaben
- Gameplay-API wird diese Komplexität kapseln
- Events sind sekundär, State-Änderungen primär transaktional

### Transaction.Lifecycle Erkenntnisse

- Lifecycle beschreibt den Zustand des Transaktions-Stacks pro Thread
- NONE → kein Kontext
- OPEN → Änderungen erlaubt, nicht final
- CLOSING → CloseCallbacks, keine neuen Änderungen
- OUTER_CLOSING → globale Finalisierung
- Trennung von State-Änderung und State-Auswirkung
- Events & World-Updates erst nach OUTER_CLOSING

### TransactionContext Erkenntnisse

- Teilnehmer erhalten nur TransactionContext, nicht Transaction
- Kein Commit/Abort durch Teilnehmer möglich
- addCloseCallback → lokaler Commit/Rollback
- addOuterCloseCallback → globale Effekte nach Finalisierung
- Callbacks laufen LIFO
- Nested Transactions erlaubt, aber kontrolliert
- Engine behält immer die Kontrolle

### CloseCallback & OuterCloseCallback

- CloseCallback:
  - lokal, transaktionsbezogen
  - Commit/Rollback des eigenen States
  - keine globalen Effekte

- OuterCloseCallback:
  - nach kompletter Finalisierung
  - Events, World-Updates, Sync
  - nur einmal pro äußerer Transaktion

- Result:
  - COMMITTED oder ABORTED
  - kein Zwischenzustand
