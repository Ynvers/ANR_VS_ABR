# Étude Expérimentale : ABR vs ARN

Ce projet compare les performances temporelles de deux structures de données :
* **ABR** : Arbre Binaire de Recherche classique (non équilibré).
* **ARN** : Arbre Rouge-Noir (auto-équilibré).

Il mesure le temps de construction et de recherche dans un cas moyen (données aléatoires) et dans le pire cas (données triées).

## 📂 Structure du projet

* `src/arbre/ABR.java` : Implémentation de l'ABR.
* `src/arbre/ANR.java` : Implémentation de l'ARN (Red-Black Tree).
* `src/arbre/BenchmarkABRvsANR.java` : Classe principale pour exécuter les tests.
* `graph.py` : Script Python pour générer les courbes de résultats.
* `resultats_arbres.csv` : Fichier de données généré par le benchmark.

## ⚙️ Prérequis

* **Maven**
* **Java JDK 8** (ou supérieur).
* **Python 3** avec les bibliothèques `pandas` et `matplotlib`.
```bash
  pip install pandas matplotlib
```

## ⚙️ Compilation et Exécution

1. Compilation du code Java

Placez-vous à la racine du projet (là où se trouve le dossier src)
```
mvn compile
```

2. Lancement du Benchmark

Exécutez la classe de test. Cela va effectuer les calculs pour différentes tailles d'arbres (N allant de 1 000 à 100 000) et générer le fichier `resultats_arbres.csv`.
Bash

```
java -cp target/classes arbre.BenchmarkABRvsANR
```
Note : Pour N=100 000, le traitement du cas trié pour l'ABR peut prendre quelques secondes (complexité quadratique).

3. Génération des graphiques

Une fois le fichier CSV généré, lancez le script Python pour visualiser les résultats :
```
python graph.py
```
ou
```
python3 graph.py
```
Le fichier `graphique.png` sera créé à la racine.

## 📊 Analyse des Résultats

Le script génère 4 graphiques comparatifs :

1. **Cas Aléatoire** (Ligne du haut)
	* **Observation** : Les courbes de l'ABR (Rouge) et de l'ARN (Bleu) sont très proches.
	* **Explication** : Sur des données aléatoires, un ABR est naturellement "assez" équilibré. Sa hauteur est logarithmique (O(logN)). Les surcoûts de l'ARN (rotations) sont négligeables.

2. **Cas Trié** (Ligne du bas - Échelle Logarithmique)
	* **Observation** :
		* **ABR (Rouge)** : Le temps explose. La courbe monte drastiquement.
		* **ARN (Bleu)** : Le temps reste très bas et stable (similaire au cas aléatoire).

	* **Explication** :
		* L'ABR dégénère en une liste chaînée (hauteur = N). La construction devient O(N2) et la recherche O(N).
		* L'ARN se rééquilibre automatiquement grâce aux rotations. Sa hauteur reste bornée à 2log2​(N+1). La complexité reste optimale (O(logN)).

## 📝 Auteur

Projet réalisé dans le cadre d'une étude sur les structures de données arborescentes par [Nathan ADOHO](https://github.com/Ynvers)