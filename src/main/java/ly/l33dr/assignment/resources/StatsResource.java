package ly.l33dr.assignment.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import ly.l33dr.assignment.entities.leaderboards.Leaderboard;
import ly.l33dr.assignment.entities.statistics.StatName;
import ly.l33dr.assignment.entities.statistics.Statistic;
import ly.l33dr.assignment.entities.statistics.StatisticField;
import ly.l33dr.assignment.entities.statistics.UserStats;
import ly.l33dr.assignment.entities.users.User;
import ly.l33dr.assignment.entities.users.UserField;
import ly.l33dr.assignment.errors.AppErrorType;
import ly.l33dr.assignment.exceptions.BadRequestException;
import ly.l33dr.assignment.exceptions.NotFoundException;
import ly.l33dr.assignment.services.StatisticService;
import ly.l33dr.assignment.services.UserService;

/**
 * Resource providing statistics
 * 
 */
@Path("stats")
public class StatsResource {

    @Context
    private UriInfo uriInfo;
    private StatisticService statisticService;
    private UserService userService;

    public StatsResource() {
        statisticService = new StatisticService();
        userService = new UserService();
    }

    /**
     * Gets all statistic entries for a user name that must be provided as a
     * query parameter
     * 
     * @return a list of statistic entries as JSON
     * @throws BadRequestException
     *             if the user name was not provided as a query parameter
     * @throws NotFoundException
     *             if no user or statistic was found
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStats() throws BadRequestException, NotFoundException {

        String userName = uriInfo.getQueryParameters().getFirst(UserField.userName.getJavaName());
        if (userName == null) {
            throw new BadRequestException(AppErrorType.MISSING_QUERY_PARAM,
                    UserField.userName.getJavaName());
        }

        User user = userService.getByField(UserField.userName, userName);
        if (user == null) {
            throw new NotFoundException(AppErrorType.ENTITY_NOT_FOUND, User.class.getSimpleName(),
                    UserField.userName.getSqlName(), userName);
        }

        Set<Statistic> statistics = user.getStatistics();
        if (statistics == null || statistics.isEmpty()) {
            throw new NotFoundException(AppErrorType.ENTITY_NOT_FOUND,
                    Statistic.class.getSimpleName(), StatisticField.user.getSqlName(), user.getId());
        }

        UserStats userStats = new UserStats(userName, statistics);

        return Response.ok(userStats).build();
    }

    /**
     * Persists a statistic entry for a user name that must be provided as a
     * query parameter
     * 
     * @param statistic
     *            the entry to persist
     * @return the URI of the new resource in the header and the created
     *         resource as JSON in the body
     * @throws BadRequestException
     *             if the statistic entry contains invalid values or the user
     *             name was not provided
     * @throws NotFoundException
     *             if no user was found
     * @throws URISyntaxException
     *             if the URI could not be created
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendStats(Statistic statistic) throws BadRequestException, NotFoundException,
            URISyntaxException {

        statisticService.validate(statistic);

        String userName = uriInfo.getQueryParameters().getFirst(UserField.userName.getJavaName());
        if (userName == null) {
            throw new BadRequestException(AppErrorType.MISSING_QUERY_PARAM,
                    UserField.userName.getJavaName());
        }

        User user = userService.getByField(UserField.userName, userName);
        if (user == null) {
            throw new NotFoundException(AppErrorType.ENTITY_NOT_FOUND, User.class.getSimpleName(),
                    UserField.userName.getSqlName(), userName);
        }

        statistic.setUser(user);
        Long id = statisticService.persist(statistic);
        URI uri = new URI(String.format("%s/%s", uriInfo.getAbsolutePath().toString(), id));
        return Response.created(uri).entity(statistic).build();
    }

    /**
     * Gets statistic entry by id
     * 
     * @param id
     *            the id to be retrieved
     * @return a statistic entry for the given id as JSON
     * @throws NotFoundException
     *             if no entry was found
     */
    @GET
    @Path("{id: \\d{1,19}}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatById(@PathParam("id") Long id) throws NotFoundException {

        List<Statistic> statistics = statisticService.getByField(StatisticField.id, id);
        if (statistics == null || statistics.isEmpty()) {
            throw new NotFoundException(AppErrorType.ENTITY_NOT_FOUND,
                    Statistic.class.getSimpleName(), StatisticField.id.getSqlName(), id);
        }
        return Response.ok(statistics.get(0)).build();
    }

    /**
     * Gets the leaderboard for a given stat ordered from highest stat value to
     * lowest
     * 
     * @return a leaderboard for the given stat as JSON
     * @throws BadRequestException
     *             if the stat does not exist or was not provided
     * @throws NotFoundException
     *             if no leaderboard was found
     */
    @GET
    @Path("leaderboard")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLeaderboard() throws BadRequestException, NotFoundException {
        StatName statName = null;
        String statNameDirty = uriInfo.getQueryParameters().getFirst(
                StatisticField.statName.getJavaName());

        try {
            statName = StatName.valueOf(statNameDirty);
        } catch (IllegalArgumentException iae) {
            throw new BadRequestException(AppErrorType.ENTITY_NOT_FOUND,
                    Statistic.class.getSimpleName(), StatisticField.statName.getSqlName(),
                    statNameDirty);
        } catch (NullPointerException npe) {
            throw new BadRequestException(AppErrorType.MISSING_QUERY_PARAM,
                    StatisticField.statName.getJavaName());
        }

        Leaderboard leaderboad = statisticService.getLeaderboard(statName);

        return Response.ok(leaderboad).build();
    }

}
