package metier;

import java.util.*;

public class PyRat {

    private PathFinder pathFinder;
    private Set<Point> cheeses;
    private boolean passagePossible;
    private Set<Point> passagePossibleDirect;

    /* Méthode appelée une seule fois permettant d'effectuer des traitements "lourds" afin d'augmenter la performace de la méthode turn. */
    public void preprocessing(Map<Point, List<Point>> laby, int labyWidth, int labyHeight, Point position, List<Point> fromages) {
        cheeses = new HashSet<>(fromages);

        // créer la table de routage pour savoir si il y a un acces
        pathFinder = new PathFinder(laby, position);

        //savoir si il y a un acces sans créer une table de routage
        passagePossibleDirect = new HashSet<>();
        for (List<Point> l : laby.values()){
            passagePossibleDirect.addAll(l);
        }



    }

    /* Méthode de test appelant les différentes fonctionnalités à développer.
        @param laby - Map<Point, List<Point>> contenant tout le labyrinthe, c'est-à-dire la liste des Points, et les Points en relation (passages existants)
        @param labyWidth, labyHeight - largeur et hauteur du labyrinthe
        @param position - Point contenant la position actuelle du joueur
        @param fromages - List<Point> contenant la liste de tous les Points contenant un fromage. */



    public void turn(Map<Point, List<Point>> laby, int labyWidth, int labyHeight, Point position, List<Point> fromages) {
        Point pt1 = new Point(2,1);
        Point pt2 = new Point(3,1);
        System.out.println((fromageIci(pt1, fromages) ? "Il y a un" : "Il n'y a pas de") + " fromage ici, en position " + pt1);
        System.out.println((fromageIci_EnOrdreConstant(pt2) ? "Il y a un" : "Il n'y a pas de") + " fromage ici, en position " + pt2);
        System.out.println((passagePossible(pt1, pt2, laby) ? "Il y a un" : "Il n'y a pas de") + " passage de " + pt1 + " vers " + pt2);
        System.out.println((passagePossible_EnOrdreConstant(pt1, pt2) ? "Il y a un" : "Il n'y a pas de") + " passage de " + pt1 + " vers " + pt2);
        System.out.println("Liste des points inatteignables depuis la position " + position + " : " + pointsInatteignables(position, laby));
    }

    /* Regarde dans la liste des fromages s’il y a un fromage à la position pos.
        @return true s'il y a un fromage à la position pos, false sinon. */
    private boolean fromageIci(Point pos, List<Point> fromages){
        for (Point p : fromages){
            if (p.equals(pos)){
                return true;
            }
        }
        return false;
    }

    /* Regarde de manière performante (accès en ordre constant) s’il y a un fromage à la position pos.
        @return true s'il y a un fromage à la position pos, false sinon. */
    private boolean fromageIci_EnOrdreConstant(Point pos) {
        if (cheeses.contains(pos)){return true;}
        return false;

    }

    /* Indique si le joueur peut passer de la position (du Point) « de » au point « a ».
        @return true s'il y a un passage depuis  « de » vers « a ». */
    private boolean passagePossible(Point de, Point a, Map<Point, List<Point>> laby) {
        List<Point>chemin = new ArrayList<>();
        passagePossible = false;
        return parcoursRecursif(de, laby, chemin, a);

    }

    private boolean parcoursRecursif(Point n, Map<Point, List<Point>> laby, List<Point> chemin, Point a) {
        if (a.equals(n)){passagePossible = true;}
        chemin.add(n);
        for (Point voisin : laby.get(n)) { if (!chemin.contains(voisin)) { parcoursRecursif(voisin, laby, chemin, a);  } }
        chemin.remove(n);
        return passagePossible;
    }

    private boolean passagePossible_EnOrdreConstant(Point de, Point a){
        return passagePossibleDirect.contains(de) && passagePossibleDirect.contains(a);

    }
    /* Indique si le joueur peut passer de la position (du Point) « de » au point « a »,
        mais sans devoir parcourir la liste des Points se trouvant dans la Map !
        @return true s'il y a un passage depuis  « de » vers « a ». */
    private boolean passagePossible_EnOrdreConstant2(Point de, Point a) {

        return pathFinder.passagePossible(de, a);
    }

    /* Retourne la liste des points qui ne peuvent pas être atteints depuis la position « pos ».
        @return la liste des points qui ne peuvent pas être atteints depuis la position « pos ». */
    private List<Point> pointsInatteignables(Point pos, Map<Point, List<Point>> map) {
        List<Point> pointsInatteignable = new ArrayList<>();
        for (Point p: map.keySet()){
            if (!pathFinder.passagePossible(p)){pointsInatteignable.add(p);}
        }
        return pointsInatteignable;
    }
}



