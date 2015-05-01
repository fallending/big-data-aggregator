big-data-aggregator
===================

Implementation details:

- Domain Driven Design
- Java 8: lambdas, parallel stream, nio
- Spring Boot
- Lombok
- Mockito

Usage
-----

With maven:

```
mvn spring-boot:run -Drun.arguments="transactions.csv,exchangerates.csv,xiFHy,GBP"
```

or

```
java -jar aggregate-1.0.jar transactions.csv exchangerates.csv "xiFHy" GBP
```

Test results (100 partners and 100.000.000 transactions):
---------------------------------------------------------
(MacBook Pro, 2.3 GHz Intel Core i7, 16 GB 1600 MHz DDR3)
```
java -jar aggregate-1.0.jar transactions.csv exchangerates.csv "xiFHy" GBP
```

1.
<pre><code>
Getting aggregated transactions by partner...
Aggregated transactions by partner took 20165 msec
Getting aggregated transactions of partner...
1.9503290485007945E7
Aggregated transactions of partner took 9533 msec
</pre></code>
2.
<pre><code>
Getting aggregated transactions by partner...
Aggregated transactions by partner took 18008 msec
Getting aggregated transactions of partner...
1.9503290485007945E7
Aggregated transactions of partner took 9306 msec
</pre></code>
3.
<pre><code>
Getting aggregated transactions by partner...
Aggregated transactions by partner took 19945 msec
Getting aggregated transactions of partner...
1.9503290485007945E7
Aggregated transactions of partner took 10124 msec
</pre></code>
4.
<pre><code>
Getting aggregated transactions by partner...
Aggregated transactions by partner took 19185 msec
Getting aggregated transactions of partner...
1.9503290485007945E7
Aggregated transactions of partner took 9505 msec
</pre></code>
5.
<pre><code>
Getting aggregated transactions by partner...
Aggregated transactions by partner took 18776 msec
Getting aggregated transactions of partner...
1.9503290485007945E7
Aggregated transactions of partner took 9874 msec
</pre></code>
6.
<pre><code>
Getting aggregated transactions by partner...
Aggregated transactions by partner took 19273 msec
Getting aggregated transactions of partner...
1.9503290485007945E7
Aggregated transactions of partner took 10103 msec
</pre></code>
7.
<pre><code>
Getting aggregated transactions by partner...
Aggregated transactions by partner took 17662 msec
Getting aggregated transactions of partner...
1.9503290485007945E7
Aggregated transactions of partner took 9443 msec
</pre></code>
