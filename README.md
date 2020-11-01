# ServerAllocator
You can use postman to test this application. Just import the collection and make sure you run the application.

# sample Request
```yaml
{"hours":33,"cpu":100}
```
# sample Response
```yaml[
    {
        "region": "us-West",
        "total_cost": 399.76,
        "servers": {
            "4xlarge": 1,
            "10xlarge": 67
        }
    },
    {
        "region": "us-East",
        "total_cost": 399.84799999999996,
        "servers": {
            "large": 1,
            "xlarge": 1,
            "4xlarge": 1,
            "8xlarge": 1,
            "10xlarge": 70
        }
    },
    {
        "region": "asia",
        "total_cost": 399.86,
        "servers": {
            "large": 1,
            "xlarge": 2,
            "8xlarge": 169
        }
    }
]
```
