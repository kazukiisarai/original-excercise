package training.original.o05;
@FunctionalInterface
public interface ScoreCondition {
  boolean test(StudentScore score);
}
