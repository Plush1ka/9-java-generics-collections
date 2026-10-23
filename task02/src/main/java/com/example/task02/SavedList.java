package com.example.task02;

import java.io.*;
import java.util.AbstractList;
import java.util.ArrayList;

public class SavedList<E extends Serializable> extends AbstractList<E> {

    private final File file;
    private final ArrayList<E> list = new ArrayList<>();

    public SavedList(File file) {
        this.file = file;
        load();
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        E old = list.set(index, element);
        save();
        return old;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
        save();
    }

    @Override
    public E remove(int index) {
        E removed = list.remove(index);
        save();
        return removed;
    }

    @SuppressWarnings("unchecked")
    private void load() {
        if(!file.exists() || file.length() == 0){
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
            ArrayList<E> loaded = (ArrayList<E>) in.readObject();
            list.addAll(loaded);
        } catch (IOException e){
            throw new UncheckedIOException("Не удалось прочитать файл " + file, e);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Неизвестный класс элемента в файле " + file, e);
        }
    }

    private void save() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))){
            out.writeObject(list);
        } catch (IOException e){
            throw new UncheckedIOException("Не удалось записать в файл " + file, e);
        }
    }
}
