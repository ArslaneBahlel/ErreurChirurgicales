# ErreurChirurgicales

#Présentation
Le projet de cette année est simple : il s’agit de corriger les erreurs d’une base de données hospitalière
qui recense les chirurgies effectuées durant 3 années consécutives. Lors d’une opération chirurgicale,
les infirmières doivent renseigner plusieurs informations sur le tableau de bord présent dans le bloc
opératoire : l’identifiant du bloc opératoire, le nom du chirurgien qui effectue l’opération ainsi que
l’heure (et la date) de début et de fin d’intervention. On a constaté des incohérences sur la base de
données à cause de saisies éronnées. L’erreur peut aussi bien porter sur la saisie du chirurgien en charge
de l’opération ou du bloc opératoire que sur la saisie de l’heure de début ou de finn d’intervention.
Cela crée des “conflits” entre les chirurgies qu’il faut résoudre pour retrouver une base de données
cohérente. Votre mission, si vous l’acceptez (mais vous n’avez pas vraiment le choix), est de développer
une application permettant de corriger ces conlits.

#Objectifs
Vous aurez à votre disposition 2 bases de données. Une première base de petite taille vous permettra
d’implémenter les fonctionnalités de base et de tester votre programme. Une seconde, bien plus grande,
vous permettra d’éprouver votre implémentation à des erreurs atypiques qui ne répondent pas nécessairement
aux corrections de base. Vous pourrez donc améliorer votre application, étape par étape, pour
qu’elle corrige de plus en plus d’erreurs. Cependant, le but du projet n’est pas seulement de corriger le
plus grand nombre d’erreurs, il vous faudra aussi apporter des modifications cohérentes par rapport à la
base de données que vous devrez justifier le jour de la soutenance. A ce titre, un indicateur permettant
de juger de la qualité d’une correction serait apprécié.
Par ailleurs, il vous faut garder à l’esprit qu’il s’agit d’un projet qui doit vous permettre d’appliquer
et donc de consolider les connaissances que vous avez acquises lors du premier semestre. Par conséquent,
le nombre de fonctionnalités de votre application n’est pas l’unique critère de notation. L’objectif
principal de ce projet est de fournir une architecture d’application qui respecte les principes de base
du Java Objet : le découpage du code en classes cohérentes et sa non redondance. En d’autres termes,
si vous arrivez à corriger toutes les erreurs mais que votre application tient sur une seule et unique
classe : vous ne pourrez pas prétendre à une bonne note.
En ce qui concerne les spécifications logicielles, il n’y a pas de cahier des charges. Cela veut dire que
vous pouvez laisser libre court à votre créativité pour proposer les fonctionnalités qui vous paraissent
utiles. Néanmoins, pour parvenir à corriger les erreurs présentes sur les bases, vous aurez nécessairement
besoin de visualiser les conflits. Pour cette visualisation, une interface graphique n’est pas requise :
un affichage console suffit mais il doit être clair. Plus vous utiliserez les notions vues en cours lors du
premier semestre, meilleure sera votre note.

