# DirectDealing

DirectDealing est une plateforme de mise en relation pour des échanges de bons procédés entre particuliers. Que ce soit des services ou des objets, les échanges peuvent être récurrents ou non et programmés à l'avance.

## Vidéo de présentation

[Lien vers la vidéo de présentation](https://youtu.be/zf2zeCf-eFI)

## Technologies utilisées

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-0087B3?style=for-the-badge&logo=java&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=White)
![JLink](https://img.shields.io/badge/JLink-blueviolet?style=for-the-badge&logo=oracle&logoColor=white)

## Prérequis

- Java JDK 17
- Gradle 8.2 ou version supérieure

## Installation

Clonez ce dépôt sur votre machine locale en utilisant l'une des commandes suivantes :

### Via HTTPS

```
git clone https://github.com/kour0/DirectDealing.git
```

### Via SSH

```
git clone git@github.com:kour0/DirectDealing.git
```

## Configuration de l'environnement

Assurez-vous que Java et Gradle sont correctement installés sur votre système. Vous pouvez vérifier cela en exécutant les commandes suivantes dans votre terminal :

```
java -version
```

```
gradle -version
```

## Compilation et exécution

Pour compiler le projet, exécutez la commande suivante dans le répertoire racine du projet :

```
gradle build
```

Pour exécuter l'application, utilisez la commande suivante :

```
gradle run
```

## Exécution des tests

Pour lancer les tests, utilisez la commande suivante :

```
gradle test
```

## Database Seeding

Remplissez la base de données à l'aide de données de test avec la tâche `seedDatabase` :

```
gradle seedDatabase
```

## Exécution avec initialisation fraîche

Pour exécuter l'application avec une initialisation fraîche de la base de données, utilisez la tâche `freshRun` :

```
gradle freshRun
```

## Dépendances

Ce projet utilise diverses dépendances telles que Hibernate, JavaFX, Lombok, etc. Elles sont définies dans le fichier `build.gradle`.

## Packaging avec JLink

Ce projet utilise le plugin JLink pour créer une image de l'application. Pour créer une image de l'application, utilisez la commande suivante :
```
gradle jlink
```

L'image de l'application se trouvera dans le répertoire `build/distributions`.

N'hésitez pas à contribuer à ce projet en proposant des améliorations ou des nouvelles fonctionnalités !

## Licence
DirectDealing est un logiciel open source sous licence MIT. Voir le fichier LICENSE pour plus de détails.
