package source;

import java.util.Iterator;
import exceptions.BoundaryViolationException;
import exceptions.EmptyTreeException;
import exceptions.InvalidPositionException;
import exceptions.NonEmptyTreeException;
import position.ElementIterator;
import position.*;
import position.NodePositionList;
import position.PositionList;



//Um classe para a árvore ligada onde os nodos têm um número arbitrário de filhos.
public class LinkedTree<E> implements Tree<E> {
    protected TreePosition<E> root; // Referência para a raíz
    protected int size; // Número de Nodos

    // Cria uma árvore vazia
    public LinkedTree() {
        root = null; // Inicia uma árvore vazia
        size = 0;
    }

    // Retorna um número de nodos da árvore
    public int size() {
        return size;
    }

    // Retorna se a árvore está vazia
    public boolean isEmpty() {
        return (size == 0);
    }

    // Retorna se um nodo é interno
    public boolean isInternal(Position<E> v) throws InvalidPositionException {
        return !isExternal(v);
    }

    // Retorna se um nodo é externo
    public boolean isExternal(Position<E> v) throws InvalidPositionException {
        TreePosition<E> vv = checkPosition(v);
        return (vv.getChildren() == null) || (vv.getChildren().isEmpty());
    }

    // Retorna se um nodo é a raíz
    public boolean isRoot(Position<E> v) throws InvalidPositionException {
        checkPosition(v);
        return (v == root());
    }

    // Retorna a raíz da árvore
    public TreePosition<E> root() throws EmptyTreeException {
        if (root == null) throw new EmptyTreeException("The tree is empty");
        return root;
    }

    // Retorna o pai de um nodo
    public TreePosition<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
        TreePosition<E> vv = checkPosition(v);
        TreePosition<E> parentPos = vv.getParent();
        if (parentPos == null) throw new BoundaryViolationException("No parent");
        return parentPos;
    }

    // Retorna uma coleção iterável dos filhos de um nodo
    public Iterable children(Position<E> v) throws InvalidPositionException {
        TreePosition<E> vv = checkPosition(v);
        return vv.getChildren();
    }

    // Retorna uma coleção iterável dos nodos da árvore.
    public Iterable<Position<E>> positions() {
        PositionList<Position<E>> positions = new NodePositionList<Position<E>>();
        if (size != 0) preorderPositions(root(), positions);
        return positions;
    }

    // Retorna um iterator dos elementos armazenados nos nodos
    public Iterator<E> iterator() {
        Iterable<Position<E>> positions = positions();
        PositionList<E> elements = new NodePositionList<E>();
        for (Position<E> pos : positions) elements.addLast(pos.element());
        return elements.iterator();
    }

    // Troca o elemento de um nodo
    public E replace(Position<E> v, E o) throws InvalidPositionException {
        TreePosition<E> vv = checkPosition(v);
        E temp = v.element();
        vv.setElement(o);
        return temp;
    }

    // Métodos de atualização adicionais
// Adiciona um nodo raíz para uma árvore vazia
    public TreePosition<E> addRoot(E e) throws NonEmptyTreeException {
        if (!isEmpty()) throw new NonEmptyTreeException("Tree already has a root");
        size = 1;
        root = createNode(e, null, null);
        return root;
    }

    // Troca os elementos de dos nodos
    public void swapElements(Position<E> v, Position<E> w) throws InvalidPositionException {
        TreePosition<E> vv = checkPosition(v);
        TreePosition<E> ww = checkPosition(w);
        E temp = w.element();
        ww.setElement(v.element());
        vv.setElement(temp);
    }

    // Métodos auxiliares
// Se v é um bom nodo da árvore, cast para TreePosition, caso contrário, lança exceção
    protected TreePosition<E> checkPosition(Position<E> v) throws InvalidPositionException {
        if (v == null || !(v instanceof TreePosition)) throw new InvalidPositionException("The position is invalid");
        return (TreePosition<E>) v;
    }

    // Cria um novo nodo da árvore
    protected TreePosition<E> createNode(E element, TreePosition<E> parent, PositionList<Position<E>> children) {
        return new TreeNode<E>(element, parent, children);
    }

    // Cria uma lista armazenando os nodos das subárvore de um nodo
// ordenado de acordo com a travessia das subárvores




    public String toString(LinkedTree<String> Tree) {
        String s = "";
        for (String i : Tree) { s += ", " + i; }
// OU
// for t();) {
// s += ", " + it.next();(Iterator<String> it = T.iterator(); it.hasNex
// }
        s = (s.length() == 0 ? s : s.substring(2));
        return "[" + s.toString() + "]";
    }


    public String parentheticRepresentation(Tree<E> T, Position<E> v) {
    String s = v.element().toString();
    if (T.isInternal(v)) {
        Boolean firstTime = true;
        for (Position<E> w : T.children(v)) {
            if (firstTime) {
                s += "(\n" + parentheticRepresentation(T, w);
                firstTime = false;
            } else {
                s += "," + parentheticRepresentation(T, w);
            }
            s += ")";
        }
    }
    return s;
    }

    public int depth(LinkedTree T, TreePosition v) {
        if (v == (TreeNode) T.root()) {return 0;}

        TreeNode w = (TreeNode) v.getParent();
        return depth(T, w);

    }

    protected void preorderPositions(Position<E> v, PositionList<Position<E>> pos)
            throws InvalidPositionException {
        pos.addLast(v);
        for (Object w : children(v)) preorderPositions((Position<E>) w, pos);
    }


    public void preOrder(LinkedTree<String> T, TreeNode v) {
        Position vv = checkPosition(v);

        for (Position<String> w : T.root().getChildren()) {
            preOrder(T, (TreeNode) w);
        }
    }

    public String toStringPostorder(LinkedTree T, TreePosition v) {
        String s = "";
        for (Object w : children(v)) {
            toStringPostorder(T, (TreePosition) w);

        }

        Position vv = checkPosition(v);
        s += v.element().toString();
        return s;
    }


    public int height1(LinkedTree T) {
        int h = 0;

        for (Object v : T.positions()) {
            if (isExternal((TreePosition)v)) {
                h = Math.max(h, depth(T, (TreePosition) v));
            }

        }



        return h;
    }


    public int height2(LinkedTree T, TreePosition v) {

        if (isExternal(v)) {
            return 0;
        }
        int h = 0;
        for (Object w : T.children(v)) {
            h = Math.max(h, height2(T, (TreePosition) w));
        }

        return h + 1;
    }

}





