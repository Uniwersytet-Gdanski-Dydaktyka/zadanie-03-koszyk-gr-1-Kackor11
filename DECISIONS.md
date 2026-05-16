# Decyzje Architektoniczne - Koszyk Internetowy JavaMarkt

## 1. Wybór wzorca projektowego dla Promocji (Uwaga 2)
**Decyzja:** Zastosowano wzorzec **Strategy** (Strategia).

**Uzasadnienie:**
Wzorzec Strategy został wybrany, ponieważ pozwala na hermetyzację różnych algorytmów (promocji) w osobnych klasach implementujących wspólny interfejs `Promotion`. Zgodnie z wymaganiami, promocje mogą być dynamicznie dodawane i usuwane w trakcie działania programu.
Dzięki temu podejściu, klasa `Cart` pozostaje zamknięta na modyfikacje, a system jest otwarty na rozszerzenia (Open/Closed Principle). Wzorzec Command nie został użyty, ponieważ w obecnych wymaganiach nie było potrzeby kolejkowania operacji, ich opóźnionego wykonywania ani mechanizmu cofania (undo), do których Command nadaje się najlepiej.

## 2. Mutowalność klasy Product (Uwaga 4)
**Decyzja:** Klasa `Product` została zaprojektowana jako **niemutowalna (immutable)**.

**Uzasadnienie:**
Wszystkie pola klasy `Product` są zadeklarowane z modyfikatorem `final`, a klasa nie posiada setterów. Jest to świadoma decyzja projektowa, która:
1. Gwarantuje bezpieczeństwo danych (thread-safety) i zapobiega przypadkowej zmianie stanu (np. ceny bazowej) przez inne komponenty systemu.
2. Współpracuje idealnie z nakładaniem promocji oraz klasą `PromotionOptimizer`. Nakładanie zniżek nie niszczy oryginalnego obiektu, lecz tworzy jego nową, przecenioną instancję. Dzięki temu optymalizator może bezpiecznie testować różne permutacje promocji na "wirtualnych" koszykach, nie ryzykując trwałego zepsucia danych (tzw. efektów ubocznych).

## 3. Typ danych dla kwot pieniężnych (Uwaga o double)
**Status:** Zastosowano typ `double` wyłącznie na potrzeby uproszczenia w zadaniu akademickim. Mamy pełną świadomość, że w systemach produkcyjnych do operacji finansowych należy stosować `BigDecimal` lub operować na całkowitych wartościach groszowych (typ `long`), aby uniknąć błędów precyzji arytmetyki zmiennoprzecinkowej.