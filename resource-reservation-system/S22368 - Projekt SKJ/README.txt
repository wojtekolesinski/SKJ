kompilacja projektu:

	javac AllocationRequest.java
	javac NetworkClient.java

uruchamianie projektu:
    zgodnie z założeniami zadania

    java NetworkNode -ident <identyfikator> -tcpport <numer portu TCP> -gateway <adres>:<port> <lista zasobów>

    gdzie:
        • -ident <identyfikator> określa identyfikator danego węzła (liczba naturalna, unikalna
        w obrębie sieci).
        • -tcpport <numer portu TCP> określa numer portu TCP na którym dany węzeł sieci
        oczekuje na połączenia od klientów.
        • -gateway <adres>:<port> oznacza adres IP oraz numer portu TCP, na którym oczekuje
        jeden z już uruchomionych węzłów sieci. Dla pierwszego węzła sieci parametru tego nie
        podaje się.
        • <lista zasobów> oznacza niepustą listę zasobów jakimi dysponuje dany węzeł w postaci
        par: <typ zasobu>:<liczba>, gdzie <typ zasobu> to jednoliterowy identyfikator typu zasobu a <liczba> określa liczbę sztuk tego zasobu przypisanych do danego węzła.

uruchomienie testów:

    w katalogu src na uruchomić polecenie (dla systemów Linux/MacOS):

    for test in $(ls *.sh) ; do
            echo "test $(echo "$test"| cut -d "-" -f 2-5 | tr -d ".sh")";
            sh "$test"; sleep 2;
            echo "";
    done

zaimplementowano:
    1. uruchamianie, podłączanie kolojnych węzłów, terminacja całej sieci
    2. odbieranie żądań od klienta i wykonanie rezerwacji przy założeniu, że wybrany węzeł kontaktowy posiada wolne zasoby w wymaganej liczności

nie działa:
    1. dowolna alokacja zasobów


Opis implementacji:
    Węzły sieciowe komunikują się ze sobą przy pomocy protokołu TCP. Przy utworzeniu węzeł wysyła do swojego rodzica komunikat
    HELLO, w odpowiedzi dostają port węzła u korzenia, gdzie również wysyłają komunikat HELLO, po czym są dodawane
    do listy węzłów w węźle u korzenia.

Komunikaty nadawane przez węzły:

    HELLO <identyfikator węzła>:<ip węzła>:<port węzła> - działanie opisane wyżej, po tym komunikacie węzeł u korzenia dodaje
        węzeł do listy węzłów dzieci, oraz drukuje na konsole listę dostępnych węzłów

    TERMINATE - wysyłane przez klienta do węzła, który przekazuje komunikat do węzła u korzenia lub jeśli jest węzłem u korzenia
        przekazuje komunikat TERMINATED do wszystkich węzłów dzieci, a następnie sam kończy działanie

    TERMINATED - komunikat rozsyłany przez węzeł u korzenia, terminuje działanie węzła

    LIST - komunikat wysyłany do węzła u korzenia, w odpowiedzi dostajemy listę węzłów dzieci w formacie "<identyfikator węzła>:<ip węzła>:<port węzła>"
        każdy węzeł wysyłany jest w nowej linijce, odpowiedź kończy się pustą linijką


Komunikaty nadawane przez klienta:

    TERMINATE - działanie tak, jak wyżej

    <identyfikator klienta> <identyfikator zasobu>:<liczba> [<identyfikator zasobu>:<liczba>] - zapytanie o przydzielenie zasobów
        jeśli węzeł posiada dane zasoby, odpowiada komunikatem:

        ALLOCATED
        <zasób>:<liczność>:<ip węzła>:<port węzła>
        [<zasób>:<liczność>:<ip węzła>:<port węzła>]

        w przeciwnym wypadku otrzymamy jedną linijkę:

        FAILED







