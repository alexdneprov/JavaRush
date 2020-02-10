package com.javarush.task.task37.task3711;

public class Computer {

    private CPU cpu;
    private Memory memory;
    private HardDrive hardDrive;

    Computer () {
        this.cpu = new CPU();
        this.memory = new Memory();
        this.hardDrive = new HardDrive();
    }

    void run () {
        cpu.calculate();
        memory.allocate();
        hardDrive.storeData();
    }
}
