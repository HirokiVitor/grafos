BCC6001 - Atividade Pratica 2

Estrutura do projeto:

atividade_pratica_2_java_organizado/
├── src/
│   ├── Main.java
│   ├── Graph.java
│   ├── Vertex.java
│   ├── GexfReader.java
│   └── BetweennessCentralityCalculator.java
├── data/
│   └── LesMiserables.gexf
└── output/
    └── saida.txt

Como compilar:

javac -d bin src/*.java

Como executar:

java -cp bin Main data/LesMiserables.gexf output

Observacao:
O primeiro argumento e o caminho do arquivo de entrada .gexf.
O segundo argumento e a pasta onde o arquivo saida.txt sera gerado.
