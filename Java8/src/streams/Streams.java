package streams;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Streams {
	private enum Status {
		OPEN, CLOSED
	};
	private static final class Task {
		private final Status status;
		private final Integer points;
		Task(final Status status, final Integer points) {
			this.status = status;
			this.points = points;
		}
		public Integer getPoints() { return points;  }
		public Status getStatus() { return status; }
		@Override
		public String toString() {
			return String.format("[%s, %d]", status, points);
		}

	}
	
	final static  Collection<Task> tasks = Arrays.asList(
				new Task(Status.OPEN,5),
				new Task(Status.OPEN,13),
				new Task(Status.CLOSED,8)
			);

	final static long totalPointsOfOpenTasks = tasks
										.stream().filter(task -> task.getStatus()==Status.OPEN)
										.mapToInt( Task::getPoints )
										.sum();
	
	// Calculate total points of all tasks

		final static double totalPoints = tasks
		   .stream()
		   .parallel()
		   .map( task -> task.getPoints() ) // or map( Task::getPoints )
		   .reduce( 0, Integer::sum );
		
		// Group tasks by their status

		final static Map< Status, List< Task > > map = tasks
		    .stream()
		    .collect( Collectors.groupingBy( Task::getStatus ) );
		
		// Calculate the weight of each tasks (as percent of total points)

		final static Collection< String > result = tasks
		    .stream()                                        // Stream< String >
		    .mapToInt( Task::getPoints )                     // IntStream
		    .asLongStream()                                  // LongStream
		    .mapToDouble( points -> points / totalPoints )   // DoubleStream
		    .boxed()                                         // Stream< Double >
		    .mapToLong( weigth -> ( long )( weigth * 100 ) ) // LongStream
		    .mapToObj( percentage -> percentage + "%" )      // Stream< String>
		    .collect( Collectors.toList() );                 // List< String >
		

		
	public static void main(String[] args) {
		System.out.println(totalPointsOfOpenTasks);
		System.out.println( "Total points (all tasks): " + totalPoints );
		System.out.println( map );
		System.out.println( result );
	}
}





