Hello! this is a thing made by wadwdwd (discord) wadwdwd1 on github!


for questions/support
join 
https://discord.gg/jUmqKWnQKg


STEPS TO MAKE A SERVER!!!!!

1. create 3 new terminals
Paste this in the first one

cd limbo && java -jar server.jar

Paste this in the second

cd server && java -jar server.jar

paste this in the 3rd

cd velocity && java -jar server.jar

Then go to ports, and set the visibilty of 25567 to public.
ENJOY!

Note the server is on the direct ip

SAFE START/STOP/STATUS
----------------------
Use the included scripts instead of running servers manually.

- Start all services: `./startup.sh`
- Stop all services: `./stop.sh`
- Check status: `./status.sh`

If using systemd, copy `systemd/eaglercraft.service` to `/etc/systemd/system/` and enable it:

```bash
sudo cp systemd/eaglercraft.service /etc/systemd/system/
sudo systemctl daemon-reload
sudo systemctl enable eaglercraft.service
sudo systemctl start eaglercraft.service
```

Then manage with:

```bash
sudo systemctl status eaglercraft.service
sudo systemctl stop eaglercraft.service
sudo systemctl restart eaglercraft.service
```
