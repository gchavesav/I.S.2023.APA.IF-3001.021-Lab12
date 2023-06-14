/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.list;

/**
 * Lista enlazada simple
 * @author Profesor Lic. Gilberth Chaves A.
 */
public class SinglyLinkedList implements List {
    private Node first; //apunta al inicio de la lista
    
    //Constructor
    public SinglyLinkedList(){
        this.first = null;
    }

    @Override
    public int size() throws ListException {
        if(isEmpty()){
            throw new ListException("Singly Linked List is empty");
        }
        Node aux = first;
        int count=0;
        while(aux!=null){
            count++;
            aux = aux.next; //lo movemos al sgte nodo
        }
        return count;
    }

    @Override
    public void clear() {
        first = null;
    }

    @Override
    public boolean isEmpty() {
        return first==null;
    }

    @Override
    public boolean contains(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("Singly Linked List is empty");
        }
        Node aux = first;
        while(aux!=null){
            if(util.Utility.compare(aux.data, element)==0){
                return true;
            }
            aux = aux.next; //lo movemos al sgte nodo
        }
        return false; //indica q el elemento no existe
    }

    @Override
    public void add(Object element) {
        Node newNode = new Node(element);
        if(isEmpty()){
            first = newNode;
        }
        else{
            Node aux = first;
            //me muevo por la lista hasta el ultimo elemento
            while(aux.next!=null){
                aux = aux.next;
            }
            //cuando se sale del while aux.sgte == null
            aux.next = newNode;
        }
    }

    @Override
    public void addFirst(Object element) {
        Node newNode = new Node(element);
        if(isEmpty()){
            first = newNode;
        }
        newNode.next = first;
        first = newNode;
    }

    @Override
    public void addLast(Object element) {
        add(element);
    }

    @Override
    public void addInSortedList(Object element) {
    }

    @Override
    public void remove(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("Singly Linked List is empty");
        }
        //caso1. El elemento a suprimir es el primero
        if(util.Utility.compare(first.data, element)==0){
            first = first.next;
        }
        //caso2. Elemento puede estar en medio o al final
        else{
            Node prev = first; //anterior
            Node aux = first.next;
            while(aux!=null&&!(util.Utility.compare(aux.data, element)==0)){
                prev = aux;
                aux = aux.next;
            }
            //se sale cuando alcanza nulo
            //o cuando encuentra el elemento a suprimir
            if(aux!=null&&util.Utility.compare(aux.data, element)==0){
                //desenlanza el nodo
                prev.next = aux.next;
            }
        }
    }

    @Override
    public Object removeFirst() throws ListException {
        if(isEmpty()){
            throw new ListException("Singly Linked List is empty");
        }
        Object element = first.data;
        first = first.next; //muevo el apuntador al sgte nodo
        return element;
    }

    @Override
    public Object removeLast() throws ListException {
        if(isEmpty()){
            throw new ListException("Singly Linked List is empty");
        }
        Node aux = first;
        Node prev = first; //anterior
        while(aux.next!=null){
            prev = aux; //dejo un rastro al nodo anterior
            aux = aux.next; //lo movemos al sgte nodo
        }
        //aux esta en el ultimo nodo
        Object element = aux.data; //es el ultimo en la lista
        prev.next = null; //elimino el ultimo nodo
        return element;
    }
    
    @Override
    public void sort() throws ListException {
        if(isEmpty())
            throw new ListException("Singly Linked List is empty");
        for(int i=1;i<=size();i++){
    	     for(int j=i+1;j<size();j++){
                 if(util.Utility.compare(getNode(j).data, getNode(i).data)<0){
                    Object aux=getNode(i).data;
                    getNode(i).data=getNode(j).data;
                    getNode(j).data=aux;
                }//if
            }//for j
        }//for i
    }

    @Override
    public int indexOf(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("Singly Linked List is empty");
        }
        Node aux = first;
        int index=1;
        while(aux!=null){
            if(util.Utility.compare(aux.data, element)==0){
                return index;
            }
            index++;
            aux = aux.next; //lo movemos al sgte nodo
        }
        return -1; //indica q el elemento no existe
    }
    
    @Override
    public Object getFirst() throws ListException {
        if(isEmpty()){
            throw new ListException("Singly Linked List is empty");
        }
        return first.data;
    }

    @Override
    public Object getLast() throws ListException {
        if(isEmpty()){
            throw new ListException("Singly Linked List is empty");
        }
        Node aux = first;
        while(aux.next!=null){
            aux = aux.next; //lo movemos al sgte nodo
        }
        return aux.data; //es el ultimo en la lista
    }    

    @Override
    public Object getPrev(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("Singly Linked List is empty");
        }
        if(util.Utility.compare(first.data, element)==0){
            return "It's the first, it has no previous";
        }
        Node aux = first;
        while(aux.next!=null){
            if(util.Utility.compare(aux.next.data, element)==0){
                return aux.data;
            }
            aux = aux.next;
        }
        return "Does not exist in single linked list";
    }

    @Override
    public Object getNext(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("Singly Linked List is empty");
        }
        Node aux = first; //dejar un rastro
        while(aux!=null){
            if(util.Utility.compare(aux.data, element)==0){
                if(aux.next!=null){
                    return aux.next.data; //el elemento posterior
                }else{
                    return "Has no next"; //no next
                }
            }
            aux = aux.next; //lo movemos al sgte nodo
        }
        return "Does not exist in single linked list";
    }

    @Override
    public Node getNode(int index) throws ListException {
        if(isEmpty()){
            throw new ListException("Singly Linked List is empty");
        }
        Node aux = first;
        int i = 1; //posicion del primer nodo
        while(aux!=null){
            if(util.Utility.compare(i, index)==0){
                return aux;
            }
            i++;
            aux = aux.next; //lo movemos al sgte nodo
        }
        return null; //si llega aqui no encontro el nodo
    }

    /**
     * Este metodo permite localizar un elemento almacenado
     * en la lista enlazada
     * retornar el nodo con el objeto, si lo encontro
     * sino retornar null
     * @param element
     * @return
     * @throws domain.list.ListException
     */
    public Node getNode(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("Singly Linked List Empty");
        }
        Node aux = first;
        while(aux!=null){
            if(util.Utility.compare(aux.data, element)==0){
                return aux;
            }
            aux = aux.next; //lo movemos al sgte nodo
        }
        return null; //si llega aqui no encontro el nodo
    }
    
    @Override
    public String toString() {
        String result = "\nSingly Linked List Content\n";
        Node aux = first;
        while(aux!=null){
            result+=aux.data+" ";
            aux = aux.next;
        }
        return result;
    }

}
