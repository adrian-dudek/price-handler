# price-handler

<ul>
<li>Hexagonal architecture was used (to easily use different infrastructure like message broker, repository or REST controller or different format of incoming data like CSV, JSON, Objects. etc) with basic DDD on code level (to easily extend functionalities in the future).</li>
<li>For exercise setup of the system was done in the MarketPriceHandlerExerciseConfig class (which can be done also by Spring or similar framework with dependency injection mechanism or any factory).</li>
<li>Test coverage of the code is 100% and every class can be easily tested separately if needed.</li>
<li>From performance perspective (speed and memory usage) buffered reader was used to read data from CSV.</li>
<li>As repository for cache / storage data in memory ConcurrentHashMap was used (performance and concurrency perspective).</li>
</ul>