test-jdo
========

The test cases

    XmlFirstTest
    XmlSecondTest
    
demonstrate a particular issue with running an XML database in the same JVM
as another database whose objects make use of the datastore identity.

Run these using:

    mvn clean compile test
    
In the first test, the call to store/makePersistent is issued to the XML
database first.  The register method on the helper thinks that the class
which will only be stored in the second database (here HSQLDB) is relevant
to the XML store, and rejects it because datastore identity is not supported.

In the second test, the call to store/makePersistent is issued to the HSQLDB
database first.  Here there is no conflict when store is subsequently called
on the XML database.

So we get a failure in the first case, but success in the second.

NOTE:  to get this test sequence to work, I had to update the surefire plugin to 2.17.
Support for forking and not reusing the forks seems not to work well in 2.12.

The actual stack trace we are seeing in our dCache production system includes
a cause which reports something like this:

Caused by: org.datanucleus.metadata.InvalidClassMetaDataException: Class "diskCacheV111.services.TransferManagerHandlerState" : Datastore ID not supported for XML
        at org.datanucleus.store.xml.XMLMetaDataListener.loaded(XMLMetaDataListener.java:39) ~[datanucleus-xml-4.0.1.jar:na]
        at org.datanucleus.metadata.MetaDataManagerImpl.processListenerLoadingCall(MetaDataManagerImpl.java:1696) ~[datanucleus-core-4.0.1.jar:na]
        at org.datanucleus.metadata.MetaDataManagerImpl.getMetaDataForClass(MetaDataManagerImpl.java:1672) ~[datanucleus-core-4.0.1.jar:na]
        at org.datanucleus.api.jdo.metadata.JDOMetaDataManager$MetaDataRegisterClassListener.registerClass(JDOMetaDataManager.java:212) ~[datanucleus-api-jdo-4.0.1.jar:na]
        at org.datanucleus.enhancer.EnhancementHelper.registerClass(EnhancementHelper.java:370) ~[datanucleus-core-4.0.1.jar:na]
        at diskCacheV111.services.TransferManagerHandler.<clinit>(TransferManagerHandler.java:63) ~[dcache-core-2.12.0-SNAPSHOT.jar:2.12.0-SNAPSHOT]
        ... 15 common frames omitted

------------------------------------------------------------------------------
It would seem that something like this is at issue:

When the classes are loaded, the enhancement byte code calls into DataNucleus.
At this point no particular PersistenceManager is associated with the class -
it simply gets loaded by the class loader and the class initialization code
calls a static registration method.

When the XML backend is used, it registers a listener that verifies that no class
is using the datastore identity. Since it has no knowledge of which classes are
eventually going to be persisted with which PersistenceManager, it ends up
rejecting the other model class even though it will never be saved with
the XML backend.

What is not clear to me is why this only happens when the order of callouts
to the two databases is XML first.  (This may or may not be a red herring.)
