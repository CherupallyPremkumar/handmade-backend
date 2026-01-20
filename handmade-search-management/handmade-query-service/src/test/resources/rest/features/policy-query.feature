Feature: Policy Search Query Service

Scenario: Search all active policies
When I POST a REST request to URL "/q/search-policies" with payload
"""
{
	"filters" :{
		"status": "ACTIVE"
	},
	"pageNum": 1,
	"numRowsInPage": 10
}
"""
Then the http status code is 200
And the top level code is 200
And success is true

# Scenario: Search policies by country code
# When I POST a REST request to URL "/q/search-policies" with payload
# """
# {
# 	"filters" :{
# 		"countryCode": "US"
# 	},
# 	"sortCriteria" :[
# 		{"name":"effectiveDate","ascendingOrder": false}
# 	],
# 	"pageNum": 1,
# 	"numRowsInPage": 10
# }
# """
# Then the http status code is 200
# And the top level code is 200
# And success is true

# Scenario: Search policies by seller type
# When I POST a REST request to URL "/q/search-policies" with payload
# """
# {
# 	"filters" :{
# 		"sellerType": "INDIVIDUAL"
# 	},
# 	"pageNum": 1,
# 	"numRowsInPage": 10
# }
# """
# Then the http status code is 200
# And the top level code is 200
# And success is true

# Scenario: Search policies by version
# When I POST a REST request to URL "/q/search-policies" with payload
# """
# {
# 	"filters" :{
# 		"policyVersion": "2024.1-US-INDIVIDUAL"
# 	},
# 	"pageNum": 1,
# 	"numRowsInPage": 10
# }
# """
# Then the http status code is 200
# And the top level code is 200
# And success is true
# And the REST response key "numRowsReturned" is "1"
# And the REST response key "list[0].row.policyVersion" is "2024.1-US-INDIVIDUAL"

Scenario: Search policies by key (Valid)
When I POST a REST request to URL "/q/search-policies" with payload
"""
{
	"filters" :{
		"policyKey": "PRODUCT_COMPLIANCE"
	},
	"pageNum": 1,
	"numRowsInPage": 10
}
"""
Then the http status code is 200
And the top level code is 200
And success is true
And the REST response key "numRowsReturned" is "1"
And the REST response key "list[0].row.policyKey" is "PRODUCT_COMPLIANCE"

Scenario: Search draft policies
When I POST a REST request to URL "/q/search-policies" with payload
"""
{
	"filters" :{
		"isActive": false
	},
	"pageNum": 1,
	"numRowsInPage": 10
}
"""
Then the http status code is 200
And the top level code is 200
And success is true

# Scenario: Search policies with multiple filters
# When I POST a REST request to URL "/q/search-policies" with payload
# """
# {
# 	"filters" :{
# 		"countryCode": "US",
# 		"sellerType": "INDIVIDUAL",
# 		"status": "ACTIVE"
# 	},
# 	"sortCriteria" :[
# 		{"name":"effectiveDate","ascendingOrder": false}
# 	],
# 	"pageNum": 1,
# 	"numRowsInPage": 10
# }
# """
# Then the http status code is 200
# And the top level code is 200
# And success is true
