package com.gerrymander.constant;

public class ControllerAttributes {
	public static final String USER_REGULAR = "user_regular";
	public static final String USER_ADMIN = "user_admin";
	
	public static final int YEAR_FROM = 1920;
	public static final int YEAR_TO = 2016;
	
	public static final String CSV_VOTES_URL = "161208votes.csv";
	
	public static final String LOPSIDED_FAIL = "The lopsided wins test compares the win margins of the two parties. "
			+ "In states that are gerrymandered, the party that benefits from the gerrymander will win many seats by"
			+ " small margins, while the opposing party wins a few seats by overwhelming margins. The differences in"
			+ " win margins can be calculated using the two-sample t-test, the most widely used test in all of the "
			+ "sciences.\n\n In %s's %d election, Republicans won their districts with an average "
			+ "of %s percent of the vote, and Democrats won their districts with an average of %s percent "
			+ "of the vote. The difference between the two parties’ win margins indicates %s may be gerrymandered"
			+ " to gain an advantage for %s. The chance that this difference would have arisen by nonpartisan"
			+ " processes alone is %s.\n\n";
	public static final String LOPSIDED_PASS = "The lopsided wins test compares the win margins of the two parties. "
			+ "In states that are gerrymandered, the party that benefits from the gerrymander will win many seats by "
			+ "small margins, while the opposing party wins a few seats by overwhelming margins. The differences in "
			+ "win margins can be calculated using the two-sample t-test, the most widely used test in all of the "
			+ "sciences.\n\nIn %s's %d election, Republicans won their districts with an average of %s percent of the vote, "
			+ "and Democrats won their districts with an average of %s percent of the vote. The difference "
			+ "between the two parties’ win margins does not suggest that the state is gerrymandered.";
	public static final String LOPSIDED_SKIP = "The lopsided wins test compares the win margins of the two parties. "
			+ "In states that are gerrymandered, the party that benefits from the gerrymander will win many seats "
			+ "by small margins, while the opposing party wins a few seats by overwhelming margins. The differences"
			+ " in win margins can be calculated using the two-sample t-test, the most widely used test in all of the "
			+ "sciences.\n\nUnfortunately, the two-sample t-test requires that both parties win at least two seats,"
			+ " and %s won %d in %s in %d.";
	public static final String CONSISTENT_FAIL = "The consistent advantage test compares the median Democratic vote share "
			+ "across all districts to the average (arithmetic mean) Democratic vote share in %s in %d. If the median is "
			+ "larger than the average, the state may be a Democratic gerrymander. If the median is smaller than the average, "
			+ "the state may be a Republican gerrymander. \nIn %s in %d, the median Democratic vote share was %.1f%%, the "
			+ "average Democratic vote share was %.1f%%, and the difference is %.2f%%. This difference suggests %s is gerrymandered "
			+ "to gain an advantage for %s. The chance that this difference would have arisen by nonpartisan processes alone is %.2f%%\n\n";
	public static final String CONSISTENT_PASS = "The consistent advantage test compares the median Democratic vote share "
			+ "across all districts to the average (arithmetic mean) Democratic vote share in %s in %d. If the median is "
			+ "larger than the average, the state may be a Democratic gerrymander. If the median is smaller than the average, "
			+ "the state may be a Republican gerrymander. \nIn %s in %d, the median Democratic vote share was %.1f%%, the "
			+ "average Democratic vote share was %.1f%%, and the difference is %.2f%%. This difference does not suggests %s is gerrymandered. \n\n";
	public static final String CONSISTENT_SKIP = "The consistent advantage test compares the median Democratic vote share "
			+ "across all districts to the average (arithmetic mean) Democratic vote share in %s in %d. If the median is "
			+ "larger than the average, the state may be a Democratic gerrymander. If the median is smaller than the average, "
			+ "the state may be a Republican gerrymander. \nIn %s in %d, the median Democratic vote share was %.1f%%, the "
			+ "average Democratic vote share was %.1f%%, and the difference is %.2f%%. \nIt's important to note that when one "
			+ "party is dominant statewide(here is %s), the mean-median difference becomes less reliable. In that case, other "
			+ "tests for asymmetric advantage may be used, as described in Prof. Wang's Election Law Journal article.\n\n";
}
