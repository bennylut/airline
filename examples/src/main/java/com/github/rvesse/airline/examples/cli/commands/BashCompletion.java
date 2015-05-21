package com.github.rvesse.airline.examples.cli.commands;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import com.github.rvesse.airline.Command;
import com.github.rvesse.airline.examples.ExampleRunnable;
import com.github.rvesse.airline.help.cli.bash.BashCompletionGenerator;
import com.github.rvesse.airline.model.GlobalMetadata;

@Command(name = "generate-completions", description = "Generates a Bash completion script named completions.bash - the file can then be sourced to provide completion for this CLI")
public class BashCompletion implements ExampleRunnable {

    @Inject
    private GlobalMetadata global;

    @Override
    public int run() {
        try (FileOutputStream out = new FileOutputStream("completions.bash")) {
            new BashCompletionGenerator().usage(global, out);
        } catch (IOException e) {
            System.err.println("Error generating completion script: " + e.getMessage());
            e.printStackTrace(System.err);
        }
        return 0;
    }
}
