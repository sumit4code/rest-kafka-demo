db.createUser(
    {
        user: "blueyonder",
        pwd: "blueyonder",
        roles: [
            {
                role: "readWrite",
                db: "employeeDB"
            }
        ]
    }
);
