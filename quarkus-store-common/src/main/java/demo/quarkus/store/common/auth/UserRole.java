package demo.quarkus.store.common.auth;

public enum UserRole
{

    user,
    admin;

    public static UserRole fromString( final String role )
    {
        for ( UserRole userRole : UserRole.values() )
        {
            if ( userRole.name().equalsIgnoreCase( role ) )
            {
                return userRole;
            }
        }
        throw new IllegalArgumentException( "No enum constant " + UserRole.class.getCanonicalName() + "." + role );
    }
}
