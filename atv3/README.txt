BCC6001 - Atividade Pratica 3

Tema:
Emparelhamento Maximo de Empregos com Grafo Bipartido usando max-flow.

Estrutura do projeto:

atv3/
|-- src/
|   |-- Main.java
|   |-- Assignment.java
|   |-- BipartiteGraph.java
|   |-- InputReader.java
|   `-- MaxFlowMatcher.java
|-- test/
|   `-- MaxFlowMatcherTest.java
|-- data/
|   `-- candidatos-vagas.txt
`-- output/
    `-- saida.txt

Como compilar:

javac -d bin src/*.java

Como executar:

java -cp bin Main data/candidatos-vagas.txt output

Como executar os testes:

javac -d bin src/*.java test/*.java
java -cp bin MaxFlowMatcherTest

Observacao:
O primeiro argumento e o caminho do arquivo de entrada.
O segundo argumento e a pasta onde o arquivo saida.txt sera gerado.
O arquivo saida.txt contem, na primeira linha, o numero maximo de candidatos alocados.
Nas proximas linhas, cada linha contem o indice do candidato seguido pelo indice da vaga alocada.
