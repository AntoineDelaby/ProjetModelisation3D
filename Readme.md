**POUR LANCER LA JAR EXECUTABLE IL FAUT METTRE LE DOSSIER DE RESSOURCES CONTENANT LES FICHIERS AU MEME ENDROIT QUE LA JAR**

Gillot Quentin
Gallego Mathéo
Demoulin Romain
Vroland Yanis
Delaby Antoine

Groupe H5

- Description des activités/responsabilités de chaque membre de l'équipe

Pour fluidifier notre travail, nous avons réparti les tâches, ainsi :
  .Antoine s'est occupé de la génération des fichiers PLY et du montage vidéo de la vidéo de présentation du projet.

  .Yanis s'est chargé des classes de tests, a apporté plusieurs modifications majeures sur le controller.

  .Mathéo s'est chargé de résoudre différents problèmes sur le code quand les autres travaillaient sur d'autres classes.
  
  .Quentin s'est occupé de charger la liste des fichiers, de faire la description de ceux-ci et de mettre en oeuvre  les différentes méthodes de manipulation (rotation, zoom, translation).

- Toutes précisions qui vous semblent importantes pour la bonne évaluation de votre projet :

  .Romain Demoulin, ne souhaitant plus suivre la formation n'a donc pas participé à la production du projet.

  .N'ayant pas bien compris le sens du projet au démarrage, nous avions commencé à implémenter une solution avec la classe TriangleMesh, mais après avoir compris que ce n'était pas ce qui était demandé, nous avons fait la refonte du projet avec l'aide de la classe Canvas.

  .Les fiches fournies avec les explications des différentes formules pour effectuer les manipulations de points 3D dans l'espace ont été d'une grande aide car nous avons réussi à effectuer les méthodes de translation, de zoom et de rotation en un laps de temps assez court, ce qui nous a permis de combler pas mal de retard.

  .Ces méthodes sont accessibles et fonctionnelles mais ne sont pas encore pleinement optimisées pour l'utilisateur, nous devons donc encore effectuer quelques modifications pour garantir une utilisation simple.
  
  .Les fichiers contenant des données permettant l'utilisation du système RGB ne sont pas encore lisibles par l'application car nous avons tenu à nous concentrer sur les fichiers basiques en noir et blanc et leur manipulation.

 -------------------------------------------------------------------------------------------------------------------------------------------------------RENDU FINAL : 

 Notre application permet la visualisation et la manipulation de fichiers PLY. Plusieurs fichiers PLY sont stockés dans une bibliothèque qu'on retrouve sur la gauche de l'application dans une ListView . Pour pouvoir ouvrir un fichier et le manipuler par la suite il suffit de cliquer sur celui désiré dans la ListView. Une fois le fichier ouvert , on retrouve une petite description du fichier en bas à gauche de l'application avec notamment : le nom , le nombre de faces , le nombre de lignes , la date de dernière modification . 

 Il existe différentes manipulations possibles sur les fichiers PLY :
 - On peut ouvrir plusieurs fenêtres montrant le fichier grâce au bouton "Dédoubler" en dessous du bloc de description du fichier, les manipulations se font sur toutes les fenêtres ce qui montre l'efficacité du MVC.
 - les rotations : nous avons implémenté 3 rotations différentes suivant les 3 axes (x,y et z) manipulables grâce à des sliders présents à droite du bouton "Dédoubler".
 - Nous avons la possibilité de changer la couleur des faces et/ou des lignes grâce à 2 ColorPicker situés à droite des sliders servant à la rotation du modèle.
 - Nous proposons également une CheckBox permettant d'activer ou non l'éclairage sur le modèle. L'éclairage se fait selon une source de lumière se trouvant dans le coin haut-droite de la fenêtre.
 - Deux autres CheckBox sont disponibles et permettent de montrer ou non les lignes ou les faces selon le souhait de l'utilisateur.
 - Le modèle est également centré , il est affiché au centre du Canvas par défaut et possède un point de centrage défini en faisant la moyenne des points X et Y.
 - Nous avons implémenté la translation du modèle grâce à 4 boutons représentant des flèches directionnelles qui se trouvent au dessus des 3 CheckBox pour l'éclairage , ils permettent de bouger le modèle mais également de déplacer le point de centrage de celui-ci.
 - Une fonctionnalité de zoom/dézoom grâce à 2 boutons '+' et '-' se situant au dessus des flèches directionnelles de translation , cette fonctionnalité est notamment accessible via la molette.

 Nous avons décomposé notre code de manière à respecter le design pattern MVC :
 - On dispose ainsi d'un package model où se trouve la classe instanciant le Model où nous avons utilisé le design pattern Singleton permettant d'avoir un seul et unique modèle pour plusieurs vues et une classe FileRead permettant la lecture du fichier PLY et donc la récupération de différentes informations comme le nombre de sommets, le nombre de faces ,... .
 - Le package vues regroupe les fichiers FXML et une classe Vue qui permet le lancement de la fenêtre suivant la vue voulue.
 - Le package controller où se trouve la classe Controller permettant les différents traitements possibles dans l'application.
 - Un package mouvement contenant les différentes classes en rapport avec les modifications du fichier héritant d'une classe abstraite Mouvement (Rotation,Translation,Zoom) mais également des classes Vecteurs et Matrice qui sont utiles pour la première à la fonctionnalité de l'éclairage où il faut calculer tous les vecteurs normaux des faces de la figure et pour la seconde au stockage des points de la figure lors des mouvements.
 - Un package dessin contenant la classe DessinFace qui est la classe de gestion de l'affichage dans la Scene , une classe Face contenant une liste d'entiers représentant les différents sommets de la face et une classe Sommet avec 3 attributs x,y et z représentant les coordonnées de chaque sommet de la figure.
 - Un package application qui contient la classe Main permettant uniquement le lancement de l'application.

 Dans le but d'optimiser les tâches , nous avons procédé selon la méthode Agile avec des réunions régulières pour présenter les différentes modifications faites mais également planifier les futures tâches à accomplir en nous les répartissant, ainsi le travail pour le livrable final a été réparti de la façon suivante :
 - Antoine s'est chargé de la fonctionnalité visant à afficher les modèles sur plusieurs fenêtres, de l'algorithme du peintre permettant de résoudre les problèmes de visibilité des faces et a fait une refonte de l'interface du projet.
 - Mathéo a optimisé la fonctionnalité d'éclairage en apportant plus de détails , s'est occupé de faire toute la JavaDoc du projet, a égalemement effectué énormément de clean code et a élaboré plusieurs tests.
 - Yanis a mis en oeuvre le MVC du projet qui était assez long à effectuer car au premier rendu , nous nous étions focalisé sur les fonctionnalités du projet et avions entièrement négligé le MVC, il a aussi participé à l'élaboration de plusieurs tests et s'est chargé du centrage du modèle .
 - Quentin s'est chargé de la fonctionnalité de l'éclairage , de l'implémentation des couleurs des faces et des lignes ainsi que l'optimisation de l'algorithme du peintre en effectuant un tri sur les faces avant l'affichage de celle-ci pour gagner des performances au niveau de l'application. 

 Nous avons rencontré quelques problèmes au fil du projet :
 - L'élaboration du MVC était le plus gros problème puisque nous n'étions pas du tout focalisé sur celui-ci jusqu'au premier livrable et nous avons donc dû faire une énorme refonte du projet en suivant ce design pattern , cela nous a pris beaucoup de temps qui aurait pu servir à autre chose si nous avions codé en suivant celui-ci de suite.
 - Nous avons passé également beaucoup de temps sur la fonctionnalité visant au centrage du modèle, on ne voyait pas vraiment de quelle manière on pouvait procéder puis nous avons trouvé la solution en effectuant la moyenne des différents points x et y du modèle pour en déterminer le point central.
 
 Nous avons néanmoins réussi à résoudre nos problèmes en s'entraidant lorsque chacun avait des difficultés, l'apport de quelqu'un d'autre lorsqu'on est bloqué sur quelque chose aboutit souvent à la résolution du problème. 
 En cas de gros problèmes qui bloquaient la suite du projet (MVC) , nous nous mettions tous ensemble sur le problème pour le résoudre au plus vite et pouvoir se concentrer ensuite sur les autres problèmes.


  
