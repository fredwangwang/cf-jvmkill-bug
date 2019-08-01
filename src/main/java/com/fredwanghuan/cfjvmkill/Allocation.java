package com.fredwanghuan.cfjvmkill;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
class Allocation implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        allocate();
    }

    void allocate() throws Exception {
        System.out.println("starting to allocate memory after 5s");
        Thread.sleep(5000);

        ArrayList<Object> obj = new ArrayList<>();

        long allocationBlock = 257;
        long upper = 2L * 1024 * 1024 * 1024 / allocationBlock; // make sure at most allocate 2G to avoid explosion on my own computer.
        for (long i = 0; i < upper; i++) {
            byte[] bytes = new byte[257];
            obj.add(bytes);
        }

        System.out.println("allocation finished...");
    }
}
