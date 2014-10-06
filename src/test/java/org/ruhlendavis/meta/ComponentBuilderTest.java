package org.ruhlendavis.meta;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ComponentBuilderTest {
    ComponentBuilder builder = new ComponentBuilder();

    @Test
    public void generateShouldNeverReturnNull() {
        assertNotNull(builder.generate(null));
        assertNotNull(builder.generate(""));
    }

    @Test
    public void generateShouldSetId() {
        String databaseReference = RandomStringUtils.randomNumeric(5);
        String input = "#" + databaseReference + "\n" + RandomStringUtils.randomAlphanumeric(13) + "\n";
        assertEquals(Long.parseLong(databaseReference), builder.generate(input).getId(), 0);
    }

    @Test
    public void generateShouldSetName() {
        String componentName = RandomStringUtils.randomAlphanumeric(13);
        String input = "#" + RandomStringUtils.randomNumeric(5) + "\n" + componentName + "\n";
        assertEquals(componentName, builder.generate(input).getName());
    }

    @Test
    public void generateShouldSetDescription() {
        String description = RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(10,50));
        String input = "#" + RandomStringUtils.randomNumeric(5) + "\n" + RandomStringUtils.randomAlphanumeric(13) + "\n"
                     + "_/de:10:" + description + "\n";
        assertEquals(description, builder.generate(input).getDescription());
    }

    public void test() {
        String example = "#0\n" +
                "The President's Office\n" +
                "-1\n" +
                "524\n" +
                "-1\n" +
                "320 0\n" +
                "1029616068\n" +
                "1412145773\n" +
                "356\n" +
                "1411597664\n" +
                "*Props*\n" +
                "@/alias/backtick:2:say (to $1) $-1\n" +
                "@/badnames/*d*a*m*n*|*f*u*c*k*|*s*h*i*t*|*p*e*n*i*s*|*b*e*a*v*i*s*|*b*u*t*h*e*a*d*|*c*u*n*y*|*c*u*n*t*|ass*|*(*#*)*:10:.\n" +
                "@/badnames/name:10:Pick a name already!\n" +
                "@/PMSInstalled:10:yes\n" +
                "@/s0:10:Use 0's for * matching, ie: 127.0.0.0 for 127.*.*.*\n" +
                "@/s1:10:Format: @set #0=@/sites/1.2.3.4 n:why... where 'n' is:\n" +
                "@/s2:10:x = BLOCKED -- cannot get to connect screen\n" +
                "@/s3:10:l = LOCKOUT -- cannot connect (without user flag)\n" +
                "@/s4:10:g = NOGUEST -- cannot connect to any guests\n" +
                "@/s5:10:r = REQUEST -- cannot request chars from site\n" +
                "@/s6:10:u = USEROK  -- set on user to allow connect from site\n" +
                "@/welcome/0 1400:2:deprecated-port\n" +
                "@/welcome/0 2054:2:screen-reader\n" +
                "@/welcome/2 131.151.0.0:10:. for Rolla's siteban so Alton doesn't get FBI\n" +
                "@heartbeat/cribbage:2:#2720\n" +
                "@heartbeat/economy:10:#132\n" +
                "@heartbeat/mobiles:2:$heartbeat/mobiles\n" +
                "@heartbeat/slapping:10:#1872\n" +
                "^/~crib/c/loss:10:7\n" +
                "^/~crib/c/pts:10:63\n" +
                "^/~crib/c/sa:10:14\n" +
                "^/~crib/c/sf:10:14\n" +
                "^/~crib/c/wins:10:7\n" +
                "^/~crib/m/loss:10:7\n" +
                "^/~crib/m/pts:10:63\n" +
                "^/~crib/m/sa:10:14\n" +
                "^/~crib/m/sf:10:14\n" +
                "^/~crib/m/wins:10:7\n" +
                "^/~crib/y/loss:10:7\n" +
                "^/~crib/y/pts:10:63\n" +
                "^/~crib/y/sa:10:14\n" +
                "^/~crib/y/sf:10:14\n" +
                "^/~crib/y/wins:10:7\n" +
                "^/~sys/dumpinterval:10:7200\n" +
                "^/~sys/lastcleantime:10:1080829600\n" +
                "^/~sys/lastdumpdone:10:1080828494\n" +
                "^/~sys/lastdumptime:10:1080828494\n" +
                "^/~sys/max_connects:10:10\n" +
                "^/~sys/maxpennies:10:99999999\n" +
                "^/~sys/shutdowntime:10:1080436735\n" +
                "^/~sys/startuptime:10:1080438336\n" +
                "_/de:10:You are in Room Zero.  It's very dark here.\n" +
                "_bbs/Building Notes:10:317\n" +
                "_bbs/Cribbage Rules:10:695\n" +
                "_bbs/ElvenTown:10:1146\n" +
                "_bbs/MuAlphaGamma:10:941\n" +
                "_bbs/RhoLambda:10:602\n" +
                "_bbs/WarrenHall:10:673\n" +
                "_bbs/WizBoard:10:1414\n" +
                "_connect/bansite:5:185\n" +
                "_connect/census:5:133\n" +
                "_connect/lasthost:5:175\n" +
                "_connect/mailwarn:5:163\n" +
                "_defs/ambiguous?:10:#-2 dbcmp\n" +
                "_defs/blank:10:user \" \" notify\n" +
                "_defs/endfor:10:1 - dup not until then pop\n" +
                "_defs/errorcheck:10:not if exit then\n" +
                "_defs/firstchar:10:dup null? not if 1 strcut pop then\n" +
                "_defs/firstname:10:name \";\" strcutat pop\n" +
                "_defs/firstword:10:\" \" strcutat\n" +
                "_defs/for:10:dup if begin\n" +
                "_defs/Glow-unparseobj:10:#218\n" +
                "_defs/here:10:user begin location dup room? until\n" +
                "_defs/j-cmd-look:10:4.6\n" +
                "_defs/j-lib-fake:10:2.1\n" +
                "_defs/lastchar:10:dup strlen dup if 1 - strcut swap then pop\n" +
                "_defs/notfound?:10:#-1 dbcmp\n" +
                "_defs/null?:10:not\n" +
                "_defs/strcheck:10:swap over strlen strcut pop stringcmp not\n" +
                "_defs/strexact:10:stringcmp not\n" +
                "_defs/stringncmp:10:rot tolower rot tolower rot strncmp\n" +
                "_defs/zero?:10:dup dbref? if #0 dbcmp else 0 = then\n" +
                "_disconnect/census:5:133\n" +
                "_land:10:*FLOATING*\n" +
                "_landlord:10:University\n" +
                "_msgmacs/html:10:{if:{instr:{flags:{owner:me}},U},</xch_mudtext><img xch_mode=html>{:1}<xch_mudtext>,{:2}}\n" +
                "_msgmacs/pmatch:10:{if:{not:{:1}},{&how} PMATCH: You must supply a match string.,{parse:duh2,{filter:duh,{if:{wizzed?},{online},{contents:here,player}},{smatch:{name:{&duh}},{:1}}},{name:{&duh2}}}}\n" +
                "_msgmacs/showoutside:10:--- outside ---{nl}{name:{loc:here}}{nl}{with:desc,{prop!:_/de,{loc:here}},{if:{&desc},{eval:{&desc}}{nl}}}{with:clist,{contents:{loc:here}},{if:{count:{&clist}},Contents: {commas:{&clist},\\, and ,who,{name:{&who}}}.{nl}}}--- inside ---{nl}\n" +
                "_msgmacs/wizzed?:10:{instr:{flags:this},w}\n" +
                "_msgmacs/wrap:10:{wrap-text:{eval:{list:{:1},{default:{:2},this}}}}\n" +
                "_msgmacs/wrap-text:10:{muf:$wrap,{:1}}\n" +
                "_prefs/sweepsleepers:10:yes\n" +
                "_prefs/sweepsleepersmsg:10:University rent-a-cops come and stand over %o, shaking their heads.  They pick %N up and haul %o off to the dorms.\n" +
                "_reg/chat/chatobject:5:966\n" +
                "_reg/cmd/3who:5:6\n" +
                "_reg/cmd/announce:5:2333\n" +
                "_reg/cmd/ansi:5:184\n" +
                "_reg/cmd/archive:5:14\n" +
                "_reg/cmd/backtrace:5:20\n" +
                "_reg/cmd/bbs:5:655\n" +
                "_reg/cmd/change:5:24\n" +
                "_reg/cmd/cinfo:5:390\n" +
                "_reg/cmd/compass:5:640\n" +
                "_reg/cmd/conhistory:5:26\n" +
                "_reg/cmd/detail:5:28\n" +
                "_reg/cmd/emotion:5:642\n" +
                "_reg/cmd/enterdesc:5:649\n" +
                "_reg/cmd/exits:5:30\n" +
                "_reg/cmd/find:5:31\n" +
                "_reg/cmd/getsite:5:33\n" +
                "_reg/cmd/globals:5:36\n" +
                "_reg/cmd/glow-chat-system:5:1242\n" +
                "_reg/cmd/hotel:5:1063\n" +
                "_reg/cmd/jerks:5:38\n" +
                "_reg/cmd/laston:5:40\n" +
                "_reg/cmd/lastsite:5:238\n" +
                "_reg/cmd/look:5:52\n" +
                "_reg/cmd/lsedit:5:45\n" +
                "_reg/cmd/mail:5:391\n" +
                "_reg/cmd/managers:5:727\n" +
                "_reg/cmd/mereg:5:67\n" +
                "_reg/cmd/morethan:5:69\n" +
                "_reg/cmd/morph:5:2209\n" +
                "_reg/cmd/muftimer:5:71\n" +
                "_reg/cmd/mv-cp:5:73\n" +
                "_reg/cmd/notify:5:75\n" +
                "_reg/cmd/null:5:698\n" +
                "_reg/cmd/oldies:5:750\n" +
                "_reg/cmd/page:5:391\n" +
                "_reg/cmd/parentcleaner:5:79\n" +
                "_reg/cmd/pcreate:5:80\n" +
                "_reg/cmd/player-management:5:88\n" +
                "_reg/cmd/playerlist:5:500\n" +
                "_reg/cmd/pms:5:88\n" +
                "_reg/cmd/purgeplayer:5:1991\n" +
                "_reg/cmd/quiz:5:227\n" +
                "_reg/cmd/read:5:1058\n" +
                "_reg/cmd/recycle:5:127\n" +
                "_reg/cmd/register:5:5\n" +
                "_reg/cmd/restart:5:82\n" +
                "_reg/cmd/roomcheck:5:84\n" +
                "_reg/cmd/say:5:86\n" +
                "_reg/cmd/score:5:1819\n" +
                "_reg/cmd/setmotd:5:87\n" +
                "_reg/cmd/show-exits:5:1869\n" +
                "_reg/cmd/signup:5:350\n" +
                "_reg/cmd/social/emote-and-say:5:2907\n" +
                "_reg/cmd/spoof:5:417\n" +
                "_reg/cmd/superWHO:5:646\n" +
                "_reg/cmd/suspend:5:90\n" +
                "_reg/cmd/sweep:5:92\n" +
                "_reg/cmd/task:5:781\n" +
                "_reg/cmd/teleport:5:96\n" +
                "_reg/cmd/tell:5:94\n" +
                "_reg/cmd/twho:5:98\n" +
                "_reg/cmd/upoedit:5:424\n" +
                "_reg/cmd/vehicle:5:1220\n" +
                "_reg/cmd/watch:5:100\n" +
                "_reg/cmd/watchfor:5:100\n" +
                "_reg/cmd/who:5:102\n" +
                "_reg/cmd/whois:5:104\n" +
                "_reg/cmd/wizlist:5:251\n" +
                "_reg/con/activityhistory:5:2308\n" +
                "_reg/con/all-connect:5:106\n" +
                "_reg/con/all-disconnect:5:107\n" +
                "_reg/con/bbsindex:5:654\n" +
                "_reg/con/guest:5:921\n" +
                "_reg/con/player-management:5:88\n" +
                "_reg/con/pms:5:88\n" +
                "_reg/con/task:5:781\n" +
                "_reg/con/watch:5:101\n" +
                "_reg/examples/MUF101/HelloWorld:5:509\n" +
                "_reg/examples/MUF101/HelloWorld2:5:936\n" +
                "_reg/game/cribbage:5:692\n" +
                "_reg/game/cribbage-ladder:5:561\n" +
                "_reg/game/cribbage-ladder-view:5:892\n" +
                "_reg/game/cribbage-score:5:691\n" +
                "_reg/game/cribscore:5:691\n" +
                "_reg/game/dalmuti:5:1224\n" +
                "_reg/game/poker:5:1341\n" +
                "_reg/game/uno:5:701\n" +
                "_reg/game/yahtzee:5:686\n" +
                "_reg/heartbeat/economy:5:132\n" +
                "_reg/heartbeat/mobiles:5:2738\n" +
                "_reg/lib/case:5:47\n" +
                "_reg/lib/cmdwho:5:102\n" +
                "_reg/lib/connects:5:44\n" +
                "_reg/lib/edit:5:48\n" +
                "_reg/lib/editor:5:49\n" +
                "_reg/lib/fake:5:110\n" +
                "_reg/lib/glow:5:698\n" +
                "_reg/lib/glowstandard:5:698\n" +
                "_reg/lib/help:5:50\n" +
                "_reg/lib/html:5:785\n" +
                "_reg/lib/index:5:169\n" +
                "_reg/lib/lmgr:5:51\n" +
                "_reg/lib/look:5:52\n" +
                "_reg/lib/mail:5:77\n" +
                "_reg/lib/match:5:53\n" +
                "_reg/lib/math:5:1826\n" +
                "_reg/lib/mesg:5:54\n" +
                "_reg/lib/mesgbox:5:55\n" +
                "_reg/lib/mpi:5:171\n" +
                "_reg/lib/mucktools:5:330\n" +
                "_reg/lib/multiname:5:109\n" +
                "_reg/lib/mv-cp:5:73\n" +
                "_reg/lib/page:5:391\n" +
                "_reg/lib/player-management:5:88\n" +
                "_reg/lib/props:5:172\n" +
                "_reg/lib/propsubstr:5:57\n" +
                "_reg/lib/quickfake:5:111\n" +
                "_reg/lib/quota:5:221\n" +
                "_reg/lib/reflist:5:58\n" +
                "_reg/lib/safe-call:5:525\n" +
                "_reg/lib/showlist:5:1552\n" +
                "_reg/lib/stackdump:5:922\n" +
                "_reg/lib/stackrng:5:59\n" +
                "_reg/lib/strings:5:56\n" +
                "_reg/lib/syvel-funcs:5:237\n" +
                "_reg/lib/timestring:5:13\n" +
                "_reg/lib/unparseobj:5:1069\n" +
                "_reg/lib/UserProp:5:60\n" +
                "_reg/lib/wix:5:143\n" +
                "_reg/lib/wordwrap:5:684\n" +
                "_reg/lib/wrap:5:124\n" +
                "_reg/lib/write:5:62\n" +
                "_reg/lock/arch:5:63\n" +
                "_reg/lock/awake:5:65\n" +
                "_reg/lock/invehicle:5:1065\n" +
                "_reg/lock/mage:5:1415\n" +
                "_reg/lock/paidentry:5:889\n" +
                "_reg/lock/password:5:893\n" +
                "_reg/lock/player-management:5:88\n" +
                "_reg/lock/pms:5:88\n" +
                "_reg/lock/true:5:698\n" +
                "_reg/lock/zombie:5:2205\n" +
                "_reg/look/medal:5:1211\n" +
                "_reg/look/show-exits:5:1869\n" +
                "_reg/macro/descwrap:5:9\n" +
                "_reg/macro/longdesc:5:10\n" +
                "_reg/macro/showlist:5:12\n" +
                "_reg/macro/timestring:5:13\n" +
                "_reg/mud/succ:5:178\n" +
                "_reg/muf/cp-mv2:5:239\n" +
                "_reg/nothing:5:698\n" +
                "_reg/null:5:698\n" +
                "_reg/par/administration:5:371\n" +
                "_reg/par/campus:5:267\n" +
                "_reg/par/campusoutside:5:197\n" +
                "_reg/par/director:5:1192\n" +
                "_reg/par/funville:5:558\n" +
                "_reg/par/library:5:316\n" +
                "_reg/par/magic:5:713\n" +
                "_reg/par/minette:5:348\n" +
                "_reg/par/oncampus:5:267\n" +
                "_reg/par/parent:5:524\n" +
                "_reg/par/pegasus:5:268\n" +
                "_reg/par/rholambda:5:601\n" +
                "_reg/par/tinyhall:5:283\n" +
                "_reg/par/warren:5:349\n" +
                "_reg/room/lostfound:5:814\n" +
                "_reg/things/local-programs:5:1829\n" +
                "_reg/things/standard-programs:5:389\n" +
                "_reg/www/authenticate:5:1674\n" +
                "_reg/www/dbref:5:1867\n" +
                "_reg/www/player-management:5:1345\n" +
                "_reg/www/pms:5:1345\n" +
                "_sweep/public?:10:yes\n" +
                "_teleport/galiases/games:2:673\n" +
                "_teleport/galiases/library:10:300\n" +
                "_teleport/galiases/quad:10:257\n" +
                "_teleport/ok:10:yes\n" +
                "_teleport/worldonly:10:no\n" +
                "_world#:10:4\n" +
                "_world#/1:10:Freshman Year, Courses concerning Building, MUCK Theory, #15\n" +
                "_world#/2:10:Sophomore Year, Courses concerning MPI, #16\n" +
                "_world#/3:10:Junior Year, Courses concerning MUF, #17\n" +
                "_world#/4:10:Senior Year, Courses concerning Administration, #18\n" +
                "day:3:12459\n" +
                "dbref:2:/@heartbeat/cribbage:#2720\n" +
                "desc#:10:1\n" +
                "desc#/1:10:{list:border}{nl}{list:desc1}{nl}{list:border}\n" +
                "exitlist_end:10:.\n" +
                "exitlist_start:10:    Paths: \n" +
                "unoleague/reset-month:3:11\n" +
                "unoleague/reset-year:3:2008\n" +
                "~/p/sex:10:neuter\n" +
                "~/p/species:10:human\n" +
                "~connect/activity:5:2308\n" +
                "~connect/all:10:$con/all-connect\n" +
                "~connect/bbsindex:10:$con/bbsindex\n" +
                "~connect/concount:10:$con/count\n" +
                "~connect/gloria:2:$cmd/glow-chat-system\n" +
                "~connect/guest:10:$con/guest\n" +
                "~connect/pms:10:$con/pms\n" +
                "~connect/rms:10:$con/task\n" +
                "~crib/c/loss:3:12\n" +
                "~crib/c/pts:3:108\n" +
                "~crib/c/sa:3:24\n" +
                "~crib/c/sf:3:24\n" +
                "~crib/c/wins:3:12\n" +
                "~crib/m/loss:3:12\n" +
                "~crib/m/pts:3:108\n" +
                "~crib/m/sa:3:24\n" +
                "~crib/m/sf:3:24\n" +
                "~crib/m/wins:3:12\n" +
                "~crib/y/loss:3:12\n" +
                "~crib/y/pts:3:108\n" +
                "~crib/y/sa:3:24\n" +
                "~crib/y/sf:3:24\n" +
                "~crib/y/wins:3:12\n" +
                "~disconnect/activity:5:2308\n" +
                "~disconnect/all:10:$con/all-disconnect\n" +
                "~disconnect/gloria:2:$cmd/glow-chat-system\n" +
                "~idle/watch:10:$con/watch\n" +
                "~look/medal:10:1211\n" +
                "~look/obviousexits:10:1869\n" +
                "~manager:10:Legacy\n" +
                "~sys/dumpinterval:3:3600\n" +
                "~sys/lastcleantime:3:1412144948\n" +
                "~sys/lastdumpdone:3:1412142185\n" +
                "~sys/lastdumptime:3:1412145781\n" +
                "~sys/max_connects:3:3\n" +
                "~sys/maxpennies:3:99999999\n" +
                "~sys/shutdowntime:3:1412099307\n" +
                "~sys/startuptime:3:1412113357\n" +
                "~unidle/watch:10:$con/watch\n" +
                "~who/idle:10:Sleeping in Class\n" +
                "~who/poll:10:Studying...\n" +
                "~world:10:*FLOATING*\n" +
                "*End*\n";
    }
}
