package com.github.rvesse.airline.examples.cli;

import com.github.rvesse.airline.Cli;
import com.github.rvesse.airline.Cli.CliBuilder;
import com.github.rvesse.airline.examples.ExampleExecutor;
import com.github.rvesse.airline.examples.ExampleRunnable;
import com.github.rvesse.airline.examples.cli.commands.BashCompletion;
import com.github.rvesse.airline.examples.cli.commands.Help;
import com.github.rvesse.airline.examples.inheritance.Child;
import com.github.rvesse.airline.examples.inheritance.GoodGrandchild;
import com.github.rvesse.airline.examples.inheritance.Parent;
import com.github.rvesse.airline.examples.simple.Simple;

/**
 * An example of creating a CLI using command groups
 */
public class CompletionCli {
    
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        //@formatter:off
        // The program name is cli
        CliBuilder<ExampleRunnable> builder = Cli.<ExampleRunnable>builder("cli")
                                                 // Add a description
                                                 .withDescription("A simple CLI with several commands available in groups")
                                                 // Add a command that uses the Help APIs to generate a completion script for our CLI
                                                 .withCommand(BashCompletion.class);
        // Add a basic group
        builder.withGroup("basic")
               .withDescription("Basic commands")
               .withCommand(Simple.class);
        // Add another group
        builder.withGroup("inheritance")
               .withDescription("Commands that demonstrate option inheritance")
               .withCommands(Parent.class, Child.class, GoodGrandchild.class);
        // You can still define top level commands as well 
        builder.withCommand(Help.class)
               .withDefaultCommand(Help.class);
        //@formatter:on
        
        ExampleExecutor.executeCli(builder.build(), args);
    }

}