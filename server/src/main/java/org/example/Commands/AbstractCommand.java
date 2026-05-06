package org.example.Commands;

import java.util.Objects;

public abstract class AbstractCommand implements Command {

    private final String name;
    private final String description;

    public AbstractCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }
        /**
         * @return Название и использование команды.
         */
        public String getName () {
            return name;
        }

        /**
         * @return Описание команды.
         */
        public String getDescription () {
            return name + ": " + description;
        }

        @Override
        public boolean equals (Object o){
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AbstractCommand command = (AbstractCommand) o;
            return Objects.equals(name, command.name) && Objects.equals(description, command.description);
        }

        @Override
        public int hashCode () {
            return Objects.hash(name, description);
        }

        @Override
        public String toString () {
            return "Command{" +
                    "name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }