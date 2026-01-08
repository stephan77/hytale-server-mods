Hytale – TransferAPI & Transaktionsmodell

Kontext für Programmierung

Autoritativer Kontext. Keine Spekulationen.
Dieses Dokument beschreibt nachweisbare Erkenntnisse aus der Analyse der Hytale TransferAPI.
Es dient ausschließlich als Wissensbasis für KI-gestützte Codegenerierung und Architekturentscheidungen.

⸻

⚠️ Status & Gültigkeitsbereich
	•	Die TransferAPI ist eine interne Infrastruktur-API
	•	Keine Gameplay-Modding-API
	•	Aktuelle SNAPSHOT-Versionen erfordern Java ≥ 25
	•	Nicht produktiv nutzbar für Mods
	•	Nur zur Analyse & Architektur-Ableitung verwenden
	•	Die offizielle Gameplay-API ist noch nicht veröffentlicht

⸻

🧱 Grundlegende Architektur-Erkenntnisse

TransferAPI ≠ Spieler-Transfer

Der Begriff Transfer bezieht sich auf:
	•	Ressourcenbewegung
	•	Inventare & Container
	•	Maschinen / Automation
	•	State- & Datenübertragung

Nicht enthalten:
	•	Spieler
	•	Welten
	•	Blöcke
	•	Events
	•	Commands
	•	Permissions

⸻

📦 Ressourcen- & Storage-Modell

Storage API
	•	Storage<T> – generisches Speicher-Interface
	•	SlottedStorage<T> – Slot-basierte Speicher (Inventare)
	•	CombinedStorage, FilteringStorage, SingleVariantStorage, …
	•	Ressourcen sind immutable
	•	Varianten werden über TransferVariant<O> modelliert

➡️ Datengetriebenes, modulares Ressourcenmodell

⸻

🔐 Transaktionssystem (Engine-Kern)

Grundprinzip
	•	Alle State-Änderungen sind transaktional
	•	Änderungen sind nicht sofort final
	•	Rollback ist Default
	•	Commit ist explizit

Transaktionen funktionieren wie Game-State-Checkpoints.

⸻

🔄 Transaction
	•	Öffnung über Transaction.openOuter()
	•	Verschachtelbar (openNested)
	•	Thread-lokal (kein Cross-Thread-Zugriff)
	•	commit() → Änderungen werden angewendet
	•	abort() / kein Commit → vollständiger Rollback

➡️ ACID-ähnliches Modell auf Engine-Ebene

⸻

🧠 Transaction Lifecycle

Zustand	Bedeutung
NONE	Kein Transaktionskontext
OPEN	Änderungen erlaubt, nicht final
CLOSING	CloseCallbacks laufen
OUTER_CLOSING	Globale Finalisierung

Wichtig:
	•	State-Änderung ≠ State-Auswirkung
	•	Events & World-Updates erst nach OUTER_CLOSING

⸻

🛡️ TransactionContext – kontrollierte Teilnahme
	•	Wird an Teilnehmer (Storages, Systeme, Mods) weitergereicht
	•	Kein Commit / Abort möglich
	•	Engine behält immer die Kontrolle

Erlaubt:
	•	openNested()
	•	nestingDepth()
	•	Callback-Registrierung

➡️ Teilnehmer dürfen mitwirken, aber nicht entscheiden

⸻

🔔 Callbacks – deterministisches Commit/Rollback

CloseCallback
	•	Wird beim Schließen der aktuellen Transaktion aufgerufen
	•	Zuständig für lokalen State
	•	Commit oder Rollback anhand von Result

❌ Keine globalen Effekte

⸻

OuterCloseCallback
	•	Wird nach Abschluss der äußersten Transaktion aufgerufen
	•	Zuständig für globale Effekte

✅ Events
✅ World-Updates
✅ Notifications
✅ markDirty

⸻

TransactionContext.Result
	•	COMMITTED
	•	ABORTED
	•	Kein Zwischenzustand
	•	Deterministisch

⸻

🧩 SnapshotParticipant
	•	Empfohlene Basisklasse für Teilnehmer
	•	Kapselt:
	•	Snapshot-Erstellung
	•	Commit
	•	Rollback

➡️ Die Gameplay-API wird auf diesem Konzept aufbauen

⸻

🚨 Verbindliche Designregeln

❌ Niemals
	•	Eigene Transaktionssysteme bauen
	•	State außerhalb eines Kontexts ändern
	•	Globale Effekte vor OUTER_CLOSING
	•	Async-State ohne Engine-Unterstützung
	•	SNAPSHOT-APIs direkt im Gameplay-Code nutzen

✅ Immer
	•	Engine Transaktionen verwalten lassen
	•	State puffern
	•	Callbacks korrekt nutzen
	•	Effekte deferren
	•	Adapter-Layer verwenden

⸻

🧠 Zentrale Erkenntnis

Hytale ist transaktionsgetrieben, nicht eventgetrieben.

	•	Events sind sekundär
	•	State-Änderungen sind primär
	•	Determinismus > Convenience
	•	Sicherheit > Schnelligkeit

⸻

🤖 spezifische Nutzungsregeln
	•	TransferAPI nicht als Gameplay-Modding-API behandeln
	•	Kein Code auf Basis von SNAPSHOT-Abhängigkeiten generieren
	•	Architektur ableiten, nicht implementieren
	•	Adapter-Pattern verwenden
	•	Auf offizielle Gameplay-API warten

⸻

✅ Zweck dieses Dokuments

Dieses Dokument ist:
	•	Kontext für 
	•	Architektur-Referenz
	•	Entscheidungsgrundlage

Es ist keine Implementierungsanleitung und kein API-Ersatz.
