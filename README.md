# Chat2

Hur man kör programmet:
    
    Programmet består av två delar 
    
    Server sidan: 
    Servern behövs köras på en maskin som är kopplat till ett nätverk som klienter är en del av.
    För att starta servern måste man köra Classen vid namn Server som har följande projektpath: src/server/Server.java
    Servern kommer lyssna på port: 1234, om så önskas går detta att justeras i Serverklassen, i sådana fall måste
    du ändra rad 12 "private static final int PORT = 1234;" ändra heltalet till det önskade värdet, säkerställ att
    Client.java med projektpath: src/application/Client.java har samma värde på rad 22: "private static final int SERVER_PORT = 1234;"
    

    Klient Sidan:
    Klienten, det vill säga chattapplikationen, startas genom att köra classen ChatGUI som har en projektpath: src/application/ChatGUI.java
    För att kunna registrera sig och logga in behöver servern köras. När du registrerar dig behöver ditt användarnamn vara minst
    4 karatärer långt och inte överskrida 30 kararktärer. 

    Följande karaktärer är ogiltiga att ha i sitt användarnamn: 
                                        "&", "=", "_", "'", "-", ",", "<", ">", "."
    
    Samma längdrestiktioner gäller för lösenord, där ogiltiga karaktärer är:
                        "(", ")", "{", "}", "[", "]", "|", "'", "´", "¬", "¦", "!", "\"", "£", "$", "%",
                        "<", ">", "&", "*", ";", ":", "#", "^", "-", "_", "~", "+", "=", ",", "@", "."
    
    
    När du har skapat en användare kan du framöver logga in med samma användare. När du har loggats in kan du på höger sida
    se användare av chattappen, du kan välja vilka användare du vill ha i chattrummet. Sen är det bara börja chatta.




























Projektets Krav:

    1) Servern ska kunna lagra
    2) Bilder ska kunna och tas emot som en del av Chat
    3) Användare som kör klienten ska kunna se historik

Analys: 

    Innan vi börjar implementera ska en analys finnas på plats (skrivit scenarion och CRC-kort). 
    Dessa ska sedan bifogas i er slutrapport som lämnas in som del av projektredovisningen
        
    CRC: 
        – Ett klassnamn
        – Klassens ansvarsområde
        – Klassens ”samarbetspartners”
        se: https://cs.ccsu.edu/~neville/Courses/Fall00/CS151/CRCCards.txt
            https://www.youtube.com/watch?v=59tkQ-FwcpA&t=62s
            https://www.youtube.com/watch?v=mbpeonZUhpU&t=140s
   

    Scenarioanalys:

        Scenariots uppgift är att kolla att problembeskrivningen är tydlig och fullständig
        Tillräckligt med tid måste avsättas för att göra analys
        Analysen leder till design. Att hitta misstag eller uteblivna detaljer redan här
        kommer att spara en väsentlig mängd arbete senare
    
Dokumentation:
    
    Dokumentera löpande och fokusera på vad, inte hur! 
    Skriv klass-kommentarer
    
    -Varför existerar klassen?
    -Vilket problem löser den och vad tar den för ansvar?
    -Skriv metodkommentarer för alla metoder med större synlighet än private
    -Beskriv metodernas övergripande syfte – inte hur de är implementerade.
